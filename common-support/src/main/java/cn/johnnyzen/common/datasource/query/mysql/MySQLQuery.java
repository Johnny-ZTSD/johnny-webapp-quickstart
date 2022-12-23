package cn.johnnyzen.common.datasource.query.mysql;

import cn.seres.bd.dataservice.common.connector.AbstractConnector;
import cn.seres.bd.dataservice.common.connector.ClickhouseConnector;
import cn.seres.bd.dataservice.common.connector.MySQLConnector;
import cn.seres.bd.dataservice.common.dto.CommonSearchDto;
import cn.seres.bd.dataservice.common.dto.page.PageResponse;
import cn.seres.bd.dataservice.common.exception.CommonException;
import cn.seres.bd.dataservice.common.postProcess.CommonPostProcessParamEnum;
import cn.seres.bd.dataservice.common.utils.JinjaUtil;
import cn.seres.bd.dataservice.model.entity.QueryJobInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/12/2 14:42
 * @description ...
 */
public class MySQLQuery extends AbstractQuery<MySQLConnector> {
    private final static Logger logger = LoggerFactory.getLogger(MySQLQuery.class);

    public MySQLQuery(MySQLConnector builtConnector){
        super(builtConnector);
    }

    @Override
    public PageResponse query(QueryJobInfo queryJobInfo, Map<String, Object> params) throws CommonException, SQLException {
        CommonSearchDto requestInfo = (CommonSearchDto) params.get(CommonPostProcessParamEnum.REQUEST_INFO.getCode());
        String sql = JinjaUtil.jinjaConvert(queryJobInfo.getSqlTemplate(), requestInfo.getDynamicPara()).replace(";", "");
//        MySQLConnector connector = new MySQLConnector(queryJobInfo.getDatasourceUrl(), queryJobInfo.getDatasourceUser(), queryJobInfo.getDatasourcePassword());
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

    @Override
    public PageResponse autoPagingQuery(QueryJobInfo queryJobInfo, Map<String, Object> params) throws CommonException, SQLException {
        CommonSearchDto requestInfo = (CommonSearchDto) params.get(CommonPostProcessParamEnum.REQUEST_INFO.getCode());
//        MySQLConnector connector = new MySQLConnector(queryJobInfo.getDatasourceUrl(), queryJobInfo.getDatasourceUser(), queryJobInfo.getDatasourcePassword());
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
            logger.debug(String.format("[getTotalSize] Time-consuming: %dms", (pageCountQueryEndTime - pageCountQueryStartTime)));

            if(pageCountQueryResult == null) {
                throw new RuntimeException("Fail to auto paging cause by empty `pageCountQueryResult`.");
            } else {
                try {
                    if(pageCountQueryResult.next()){
                        totalSize = pageCountQueryResult.getInt(1);// or getInt("cnt")
                    }
                } catch (SQLException e) {
                    logger.error("Fail to get totalSize(page-count-sql:{})", pageCountSql);
                }
            }
        } catch (SQLException e) {
            logger.error("happens a exception(SQLException) when execute count sql: {}, exception: {}", pageCountSql, e);
        }
        return totalSize;
    }
}
