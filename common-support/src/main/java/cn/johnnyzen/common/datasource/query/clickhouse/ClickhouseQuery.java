package cn.johnnyzen.common.datasource.query.clickhouse;

import cn.johnnyzen.common.datasource.connector.clickhouse.ClickhouseConnector;
import cn.johnnyzen.common.datasource.entity.QueryJobInfo;
import cn.johnnyzen.common.datasource.query.AbstractQuery;
import cn.johnnyzen.common.dto.page.PageResponse;
import cn.johnnyzen.common.exception.ApplicationRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-date 2022/8/18 13:32
 * @description db连接工具测试 - clickhouse
 */

public class ClickhouseQuery extends AbstractQuery<ClickhouseConnector> {
    private final static Logger logger = LoggerFactory.getLogger(ClickhouseQuery.class);

    public ClickhouseQuery(ClickhouseConnector builtConnector){
        super(builtConnector);
    }

    //不分页查询
    @Override
    public PageResponse query(QueryJobInfo queryJobInfo, Map<String, Object> params) throws CommonException, SQLException {
        CommonSearchDto requestInfo = (CommonSearchDto) params.get(CommonPostProcessParamEnum.REQUEST_INFO.getCode());
        String sql = JinjaUtil.jinjaConvert(queryJobInfo.getSqlTemplate(), requestInfo.getDynamicPara()).replace(";", "");
//        ClickhouseConnector connector = new ClickhouseConnector(queryJobInfo.getDatasourceUrl(), queryJobInfo.getDatasourceUser(), queryJobInfo.getDatasourcePassword());
//        connector.build();
        Connection connection = this.connector.getConnection();

        ResultSet queryResult = null;
        ResultSetMetaData resultSetMetaData = null;
        List<Map<String, Object>> responseList = new LinkedList<>();
        // 执行业务 SQL by try-with-resources grammar
        try(Statement statement = connection.createStatement()){
            long startTime = System.currentTimeMillis();
            queryResult = statement.executeQuery(sql);
            long endTime = System.currentTimeMillis();
            logger.debug(String.format("Time-consuming: %dms", (endTime - startTime)));

            resultSetMetaData = queryResult.getMetaData();

            int columns = resultSetMetaData.getColumnCount();// 结果集的列数

            while (queryResult.next()) {
                //StringBuilder lineOutput = new StringBuilder();
                Map<String, Object> record = new HashMap<>();
                for (int c = 1; c <= columns; c++) {
                    record.put(resultSetMetaData.getColumnName(c), queryResult.getObject(c));
                    //lineOutput.append( resultSetMetaData.getColumnName(c) + ":" + queryResult.getString(c) + (c < columns ? ", " : "\n") );
                }
                responseList.add(record);
                //LOG.debug(lineOutput.toString());
            }
        } catch (Exception exception) {
            logger.error("occur a exception when execute sql statement. exception: {}", exception);
        }

        PageResponse result = new PageResponse(1, Integer.MAX_VALUE, responseList, responseList.size());
        return result;
    }

    /**
     * 自动分页查询
     * @param queryJobInfo
     * @param params
     * @return
     * @throws ApplicationRuntimeException
     * @throws SQLException
     */
    @Override
    public PageResponse autoPagingQuery(QueryJobInfo queryJobInfo, Map<String, Object> params) throws ApplicationRuntimeException, SQLException {
        CommonSearchDto requestInfo = (CommonSearchDto) params.get(CommonPostProcessParamEnum.REQUEST_INFO.getCode());
//        ClickhouseConnector connector = new ClickhouseConnector(queryJobInfo.getDatasourceUrl(), queryJobInfo.getDatasourceUser(), queryJobInfo.getDatasourcePassword());
//        connector.build();
        Connection connection = this.connector.getConnection();

        Integer pageIndex = requestInfo.getPageRequest().getIndex();
        Integer pageSize = requestInfo.getPageRequest().getSize();

        String sql = renderToSql(queryJobInfo.getSqlTemplate(), requestInfo.getDynamicPara());
        String pageSql =  "select * from ( " +  sql + " ) limit " + pageSize + " offset " + (pageIndex - 1) * pageSize;
        String pageCountSql = renderToCountSql(requestInfo.getDynamicPara(), queryJobInfo);

        Integer totalSize = getTotalSize(connection, pageCountSql);
        if (totalSize == 0) {
            return new PageResponse(pageIndex, pageSize, new ArrayList<>(), totalSize);
        }

        List<Map<String, Object>> responseList = getPageResponseDataSet(connection, pageSql);
        return new PageResponse(pageIndex, pageSize, responseList, totalSize);
    }

    private String renderToSql(String sqlTemplate, Map<String, Object> dynamicPara) {
        String sql = JinjaUtil.jinjaConvert(sqlTemplate, dynamicPara).replace(";", "");;
        // 渲染2次，以解决 SQL模板 中存在 {% raw %}xx{% edraw %} 标签的渲染需求
        sql = JinjaUtil.jinjaConvert(sql, dynamicPara);
        return sql;
    }

    private String renderToCountSql(Map<String, Object> dynamicPara, QueryJobInfo queryJobInfo){
        String pageCountSql = JinjaUtil.jinjaConvert(getCountSqlTemplate(queryJobInfo), dynamicPara);
        // 渲染2次，以解决 SQL模板 中存在 {% raw %}xx{% edraw %} 标签的渲染需求
        pageCountSql = JinjaUtil.jinjaConvert(pageCountSql, dynamicPara);
        //String pageCountSql = "select count(uuid) as cnt from ( " + sql + " )";
        return pageCountSql;
    }

    /**
     * 获取响应数据集
     */
    private List<Map<String, Object>> getPageResponseDataSet(Connection connection, String pageSql){
        List<Map<String, Object>> responseList = new LinkedList<>();
        ResultSet pageQueryResult = null; // or connector.query(sql);
        ResultSetMetaData resultSetMetaData = null;

        try(Statement statement = connection.createStatement();) {
            long pageSqlStartTime = System.currentTimeMillis();
            pageQueryResult = statement.executeQuery(pageSql);// 执行业务 SQL
            long pageSqlEndTime = System.currentTimeMillis();
            logger.debug(String.format("[pageQuery] Time-consuming: %dms", (pageSqlEndTime - pageSqlStartTime)));

            resultSetMetaData = pageQueryResult.getMetaData();
            int columns = 0;// 结果集的列数
            columns = resultSetMetaData.getColumnCount();
            while (true) {
                try {
                    if(!pageQueryResult.next()) {
                        break;
                    }
                } catch (SQLException e) {
                    logger.error("resultSet is empty(pageSql: {})", pageSql, e);
                }
                //StringBuilder lineOutput = new StringBuilder();
                Map<String, Object> record = new HashMap<>();
                for (int c = 1; c <= columns; c++) {
                    try {
                        record.put(resultSetMetaData.getColumnName(c), pageQueryResult.getObject(c));
                    } catch (SQLException e) {
                        logger.error("Fail to fetch current record!(pageSql:{})", pageSql);
                    }
                    //lineOutput.append( resultSetMetaData.getColumnName(c) + ":" + queryResult.getString(c) + (c < columns ? ", " : "\n") );
                }
                responseList.add(record);
                //LOG.debug(lineOutput.toString());
            }
        } catch (SQLException e) {
            logger.error("Fail to execute Query(pageSql:{})", pageSql, e);
        }
        return responseList;
    }

    private Integer getTotalSize(Connection connection, String pageCountSql){
        Integer totalSize = 0;
        ResultSet pageCountQueryResult = null;
        try(Statement statement = connection.createStatement();) {
            long pageCountQueryStartTime = System.currentTimeMillis();
            pageCountQueryResult = statement.executeQuery(pageCountSql); // 执行分页 SQL
            long pageCountQueryEndTime = System.currentTimeMillis();
            logger.debug(String.format("[pageCountQuery] Time-consuming: %dms", (pageCountQueryEndTime - pageCountQueryStartTime)));

            if(pageCountQueryResult == null) {
                throw new RuntimeException("Fail to auto paging cause by empty `pageCountQueryResult`.");
            } else {
                if(pageCountQueryResult.next()){
                    totalSize = pageCountQueryResult.getInt(1);// or getInt("cnt")
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalSize;
    }
}