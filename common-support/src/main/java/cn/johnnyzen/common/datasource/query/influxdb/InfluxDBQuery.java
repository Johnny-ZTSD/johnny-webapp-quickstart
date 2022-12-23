package cn.johnnyzen.common.datasource.query.influxdb;

import cn.johnnyzen.common.datasource.connector.influxdb.InfluxDbConnector;
import cn.johnnyzen.common.datasource.entity.QueryJobInfo;
import cn.johnnyzen.common.datasource.query.AbstractQuery;
import cn.johnnyzen.common.dto.ResponseCodeEnum;
import cn.johnnyzen.common.dto.page.BasePage;
import cn.johnnyzen.common.dto.page.PageRequest;
import cn.johnnyzen.common.dto.page.PageResponse;
import cn.johnnyzen.common.exception.ApplicationRuntimeException;
import cn.johnnyzen.common.util.jinja.JinjaUtil;

import org.influxdb.dto.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-date 2022/7/12 16:33
 * @description db连接工具测试 - influxdb
 */

public class InfluxDBQuery extends AbstractQuery<InfluxDbConnector> {
    private final static Logger logger = LoggerFactory.getLogger(InfluxDBQuery.class);

    public InfluxDBQuery(InfluxDbConnector builtConnector){
        super(builtConnector);
    }

    public PageResponse query(String sqlTemplate, Map<String, Object> dynamicPara, InfluxDbConnector connector) throws ApplicationRuntimeException {
        String sql = JinjaUtil.jinjaConvert(sqlTemplate, dynamicPara);
        sql = JinjaUtil.jinjaConvert(sql, dynamicPara); // 渲染2次，以解决 SQL模板 中存在 {% raw %}xx{% edraw %} 标签的渲染需求

        long startTime = System.currentTimeMillis();
        QueryResult queryResult = connector.query(sql);
        long endTime = System.currentTimeMillis();

        logger.debug(String.format("Time-consuming: %dms", (endTime - startTime)));

        List<Map<String, Object>> resultList = new ArrayList<>();

        for (QueryResult.Result result : queryResult.getResults()) {
            List<QueryResult.Series> seriesList = result.getSeries();
            if(result.getError()!=null){
                String errorMessage = String.format("happens a exception when query influxdb,sql: %s, the error info is: %s", sql, result.getError());
                logger.error(errorMessage);
                throw new ApplicationRuntimeException(errorMessage);
            }
            if(seriesList!=null){
                List<Map<String, Object>> subResultList = parseOneSeriesList(seriesList);
                if(subResultList!=null && subResultList.size()>0){
                    resultList.addAll(convertDataType(subResultList));
                }
            }
        }

        PageResponse result = new PageResponse(1, Integer.MAX_VALUE, resultList, resultList.size());
        return result;
    }

    /**
     * 转换查询结果集的数据类型
     */
    public List<Map<String, Object>> convertDataType(List<Map<String, Object>> queryResult){
        return queryResult.stream().map(line -> {
            Map<String, Object> lineMap = new HashMap<>();
            line.entrySet().forEach(columnEntry -> {
                //对 Influxdb 查询结果集中可转整型(java.long.Long)且不失真的 influxdb 数值字段(java.lang.Double) 转为 java.lang.Long
                if(columnEntry.getValue() instanceof Double){
                    Double originValue = (Double) columnEntry.getValue();
                    Long targetValue = originValue.longValue();
                    if(originValue-targetValue==0){
                        lineMap.put(columnEntry.getKey(), (Object) targetValue);
                    } else {
                        lineMap.put(columnEntry.getKey(), (Object) originValue);
                    }
                } else {
                    lineMap.put(columnEntry.getKey(), columnEntry.getValue());
                }
            });
            return lineMap;
        }).collect(Collectors.toList());
    }

    /**
     * 解析子结果记录集
     */
    private List<Map<String, Object>> parseOneSeriesList(List<QueryResult.Series> seriesList){
        if(seriesList == null){
            logger.warn(ResponseCodeEnum.QUERY_RESULT_IS_EMPTY.getCode() + ":" + ResponseCodeEnum.QUERY_RESULT_IS_EMPTY.getName());
            return null;
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (QueryResult.Series series : seriesList) {
//                    String seriesName = series.getName();
            Map<String, String> tags = series.getTags();// [getTags]
            //字段名称
            List<String> columns = series.getColumns();// [getColumns]
            if (columns != null) {
                //字段值
                List<List<Object>> values = series.getValues();// [getValues]
                for (List<Object> itemList : values) { // N line records
                    Map<String, Object> map = new HashMap<>();
                    for (int i = 0; i < itemList.size(); i++) {// one line record
                        map.put(columns.get(i), itemList.get(i));
                    }
                    if (tags != null) {
                        //tag解析
                        tags.entrySet().forEach(entry -> {
                            map.put(entry.getKey(), entry.getValue());
                        });
                    }
                    resultList.add(map);
                }
            }
        }
        return resultList;
    }

    //不分页查询
    @Override
    public PageResponse query(QueryJobInfo queryJobInfo, Map<String, Object> params) throws ApplicationRuntimeException {
        String sqlTemplate = queryJobInfo.getSqlTemplate();
        Map<String, Object> requestParams = (Map<String, Object>) params.get(PageRequest.PARAMS_NAME);
//        InfluxDbConnector connector = new InfluxDbConnector(queryJobInfo.getDatasourceUrl(), queryJobInfo.getDatasourceUser(), queryJobInfo.getDatasourcePassword());
//        connector.build();
        PageResponse result = query(sqlTemplate, requestParams, this.connector);

        return result;
    }

    //自动分页查询
    @Override
    public PageResponse autoPagingQuery(QueryJobInfo queryJobInfo, Map<String, Object> params) throws ApplicationRuntimeException {
        Map<String, Object> requestParams = (Map<String, Object>) params.get(PageRequest.PARAMS_NAME);
//        InfluxDbConnector connector = new InfluxDbConnector(queryJobInfo.getDatasourceUrl(), queryJobInfo.getDatasourceUser(), queryJobInfo.getDatasourcePassword());
//        connector.build();
        Integer pageIndex = (Integer) params.get(BasePage.CURRENT_PAGE_NAME);
        Integer pageSize = (Integer) params.get(BasePage.PAGE_SIZE_NAME);


        String sql = renderToSql(queryJobInfo.getSqlTemplate(), requestParams);
        String pageSql = sql + " limit " + pageSize + " offset " + (pageIndex - 1) * pageSize;
        String pageCountSql = renderToCountSql(queryJobInfo.getCountSqlTemplate(), requestParams);
        //String pageCountSql = "select count(uuid) as cnt from ( " + sql + " )";

        //获取总记录数
        Integer totalSize = getTotalSize(pageCountSql);
        if (totalSize == 0) {
            return new PageResponse(pageIndex, pageSize, new ArrayList<>(), totalSize);
        }

        //获取分页数据集
        QueryResult queryResult = null;
        queryResult = this.connector.query(pageSql);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (QueryResult.Result result : queryResult.getResults()) {
            List<QueryResult.Series> seriesList = result.getSeries();
            List<Map<String, Object>> subResultList = parseOneSeriesList(seriesList);
            if(subResultList!=null && subResultList.size()>0){
                resultList.addAll(subResultList);
            }
        }
        return new PageResponse(pageIndex, pageSize, resultList, totalSize);
    }

    private String renderToSql(String sqlTemplate, Map<String, Object> dynamicPara) {
        String sql = JinjaUtil.jinjaConvert(sqlTemplate, dynamicPara).replace(";", "");;
        // 渲染2次，以解决 SQL模板 中存在 {% raw %}xx{% edraw %} 标签的渲染需求
        sql = JinjaUtil.jinjaConvert(sql, dynamicPara);
        return sql;
    }

    private String renderToCountSql(String countSqlTemplate,Map<String, Object> dynamicPara){
//        String pageCountSql = JinjaUtil.jinjaConvert(getCountSqlTemplate(queryJobInfo), dynamicPara);
        String pageCountSql = JinjaUtil.jinjaConvert(countSqlTemplate, dynamicPara);
        // 渲染2次，以解决 SQL模板 中存在 {% raw %}xx{% edraw %} 标签的渲染需求
        pageCountSql = JinjaUtil.jinjaConvert(pageCountSql, dynamicPara);
        //String pageCountSql = "select count(uuid) as cnt from ( " + sql + " )";
        return pageCountSql;
    }

    private Integer getTotalSize(String pageCountSql){
        QueryResult queryResult = null;
        try {
            queryResult = this.connector.query(pageCountSql);
        } catch (ApplicationRuntimeException e) {
            String errorMessage = String.format(ResponseCodeEnum.DATABASE_QUERY_ERROR.getName()  + " | occurs to exception when query count total size from influxdb. pageCountSql: %s", pageCountSql);
            logger.error(errorMessage);
            throw new ApplicationRuntimeException(ResponseCodeEnum.DATABASE_QUERY_ERROR);
        }
        Integer totalSize = 0;
        if (queryResult.getError() == null && queryResult.getResults().get(0).getError() == null && queryResult.getResults().get(0).getSeries() != null) {
            totalSize = ((Double) queryResult.getResults().get(0).getSeries().get(0).getValues().get(0).get(1)).intValue();
        }
        return totalSize;
    }
}