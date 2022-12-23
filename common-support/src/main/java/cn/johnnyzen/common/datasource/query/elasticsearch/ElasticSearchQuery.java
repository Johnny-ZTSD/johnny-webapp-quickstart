package cn.johnnyzen.common.datasource.query.elasticsearch;

import cn.johnnyzen.common.datasource.connector.elasticsearch.ElasticSearchConnector;
import cn.johnnyzen.common.datasource.entity.QueryJobInfo;
import cn.johnnyzen.common.datasource.query.AbstractQuery;
import cn.johnnyzen.common.dto.ResponseCodeEnum;
import cn.johnnyzen.common.dto.page.BasePage;
import cn.johnnyzen.common.dto.page.PageRequest;
import cn.johnnyzen.common.dto.page.PageResponse;
import cn.johnnyzen.common.exception.ApplicationRuntimeException;
import cn.johnnyzen.common.util.jinja.JinjaUtil;
import cn.johnnyzen.common.util.spring.SpringContextUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import okhttp3.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * @author johnnyzen
 * @date 2022/10/25 上午10:14
 */
public class ElasticSearchQuery extends AbstractQuery<ElasticSearchConnector> {
    private final static Logger logger = LoggerFactory.getLogger(ElasticSearchQuery.class);

    public ElasticSearchQuery(ElasticSearchConnector builtConnector){
        super(builtConnector);
    }

    @Override
    public PageResponse query(QueryJobInfo queryJobInfo, Map<String, Object> params) throws ApplicationRuntimeException {
        try {
            Map<String, Object> requestParams = (Map<String, Object>) params.get(PageRequest.PARAMS_NAME);
            String sql = JinjaUtil.jinjaConvert(queryJobInfo.getSqlTemplate(), requestParams).replace(";", "");

            logger.info("Query-SQL:{}", sql);

            long startTime = System.currentTimeMillis();

            Response response = post(queryJobInfo, sql);

            List<JsonNode> responseList = convert(response);

            logger.info("[Query] Time-consuming:{}", System.currentTimeMillis() - startTime);

            PageResponse result = new PageResponse(1, Integer.MAX_VALUE, responseList, responseList.size());
            return result;
        } catch (ApplicationRuntimeException e) {
            throw e;
        } catch (Throwable e) {
            logger.error("query Throwable!queryJobInfo:{}", queryJobInfo, e);
            throw new ApplicationRuntimeException(ResponseCodeEnum.SERVER_INTERNAL_ERROR);
        }
    }

    /**
     * 分页查询
     *
     * @param queryJobInfo
     * @param params
     * @return
     * @throws ApplicationRuntimeException
     * @throws SQLException
     */
    @Override
    public PageResponse autoPagingQuery(QueryJobInfo queryJobInfo, Map<String, Object> params) throws ApplicationRuntimeException {
        try {
            Map<String, Object> requestParams = (Map<String, Object>) params.get(PageRequest.PARAMS_NAME);

            Integer pageIndex = (Integer) params.get(BasePage.CURRENT_PAGE_NAME);
            Integer pageSize = (Integer) params.get(BasePage.PAGE_SIZE_NAME);

            String sql = JinjaUtil.jinjaConvert(queryJobInfo.getSqlTemplate(), requestParams).replace(";", "");

            // 渲染2次，以解决 SQL模板 中存在 {% raw %}xx{% edraw %} 标签的渲染需求
            sql = JinjaUtil.jinjaConvert(sql, requestParams);
            String pageSql = sql + " limit " + pageSize + " offset " + (pageIndex - 1) * pageSize;
            logger.info("PageQuery-SQL:{}", pageSql);

            String pageCountSql = JinjaUtil.jinjaConvert(getCountSqlTemplate(queryJobInfo), requestParams);
            // 渲染2次，以解决 SQL模板 中存在 {% raw %}xx{% edraw %} 标签的渲染需求
            pageCountSql = JinjaUtil.jinjaConvert(pageCountSql, requestParams);

            long pageCountQueryStartTime = System.currentTimeMillis();

            Response countResponse = post(queryJobInfo, pageCountSql);

            Integer totalSize = convertCountResult(countResponse);

            logger.info("[pageCountQuery] Time-consuming:{},totalSize:{}", System.currentTimeMillis() - pageCountQueryStartTime, totalSize);

            if (totalSize == 0) {
                return new PageResponse(pageIndex, pageSize, new ArrayList<>(), totalSize);
            }


            long pageSqlStartTime = System.currentTimeMillis();

            Response response = post(queryJobInfo, pageSql);

            List<JsonNode> responseList = convert(response);

            logger.info("[pageQuery] Time-consuming:{}", System.currentTimeMillis() - pageSqlStartTime);
            return new PageResponse(pageIndex, pageSize, responseList, totalSize);
        } catch (ApplicationRuntimeException e) {
            throw e;
        } catch (Throwable e) {
            logger.error("pageQuery Throwable!queryJobInfo:{}", queryJobInfo, e);
            throw new ApplicationRuntimeException(ResponseCodeEnum.SERVER_INTERNAL_ERROR);
        }

    }

    /**
     * 执行查询
     * @desciption
     *  因 opendistrosql 插件 的 jdc 方式(java.sql.Connection) 的不支持某些查询场景，故 ElasticSearchQuery#query/autoPagingQuery 并未使用该 connection 进行查询;
     *  而是直接使用 http 方式查询并获取 json  结果数据
     * @param queryJobInfo
     * @param sql
     * @return
     * @throws ApplicationRuntimeException
     */
    private static Response post(QueryJobInfo queryJobInfo, String sql) throws IOException, ApplicationRuntimeException {
        Map<String, String> json = new HashMap<>(8);
        json.put("query", sql);

        OkHttpClient okHttpClient = SpringContextUtil.getBean(OkHttpClient.class);

        String credential = Credentials.basic(queryJobInfo.getDatasourceUser(), queryJobInfo.getDatasourcePassword());
        Request request = new Request.Builder()
                .header("Authorization", credential)
                .method("POST", RequestBody.create(MediaType.get("application/json"), JSON.toJSONString(json)))
                .url(queryJobInfo.getDatasourceUrl())
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            ObjectMapper mapper = new ObjectMapper();
            String body = new String(response.body().bytes());

            logger.error("code:{},message:{},body:{}", response.code(), response.message(), body);

            ErrorMessage errorMessage = mapper.readValue(body, ErrorMessage.class);
            if (null != errorMessage && errorMessage.getError() != null && errorMessage.getError().getType() != null) {
                throw new ApplicationRuntimeException(ResponseCodeEnum.QUERY_ES_FAIL.getName() + "，失败原因:" + errorMessage.getError().getType());
            }

            throw new ApplicationRuntimeException(ResponseCodeEnum.QUERY_ES_FAIL);
        }
        return response;
    }

    private static Integer convertCountResult(Response response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String jsonResult = new String(response.body().bytes());
        JsonNode rootNode = mapper.readTree(jsonResult);
        JsonNode aggregations = rootNode.path("aggregations");
        if (null != aggregations && !aggregations.isEmpty()) {
            Iterator<JsonNode> iterator = aggregations.elements();
            while (iterator.hasNext()) {
                return iterator.next().get("value").asInt();
            }
        }

        return 0;
    }

    private static List<JsonNode> convert(Response response) throws IOException {
        List<JsonNode> responseList = Lists.newArrayList();

        ObjectMapper mapper = new ObjectMapper();

        String jsonResult = new String(response.body().bytes());

        // 针对聚合查询，不需要返回复杂的json结构，采用非json格式的返回结果
        QueryResult queryResult = mapper.readValue(jsonResult, QueryResult.class);
        if (null != queryResult && CollectionUtils.isNotEmpty(queryResult.getSchema())) {
            List<List<Object>> dataRows = queryResult.getDatarows();
            if (CollectionUtils.isEmpty(dataRows)) {
                return responseList;
            }

            Map<String, Object> record;
            for (int j = 0; j < dataRows.size(); j++) {
                List<Object> row = dataRows.get(j);
                record = new HashMap<>(queryResult.getSchema().size());
                for (int k = 0; k < row.size(); k++) {
                    record.put(queryResult.getSchema().get(k).name, row.get(k));
                }
                responseList.add(mapper.readTree(mapper.writeValueAsString(record)));
            }

            return responseList;
        }

        // 针对非聚合查询，需要返回复杂的json结构，采用json格式的返回结果
        JsonNode rootNode = mapper.readTree(jsonResult);
        JsonNode hits = rootNode.path("hits").path("hits");
        if (null != hits && hits.size() > 0) {
            for (int i = 0; i < hits.size(); i++) {
                responseList.add(hits.get(i).path("_source"));
            }
        }
        return responseList;
    }

    /**
     * 获取 分页统计的 模板 SQL
     *
     * @param queryJobInfo
     * @return
     */
    @Override
    public String getCountSqlTemplate(QueryJobInfo queryJobInfo) {
        String countSqlTemplate = null;
        if (queryJobInfo.getSqlSupportAutoPagable()) {
            countSqlTemplate = queryJobInfo.getCountSqlTemplate();
            if (StringUtils.isEmpty(countSqlTemplate)) {
                countSqlTemplate = "SELECT COUNT(*) AS cnt FROM (" + queryJobInfo.getSqlTemplate() + " )";
            }
        }
        return countSqlTemplate;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class QueryResult {

        private List<Column> schema;


        private List<List<Object>> datarows;


        private static class Column {
            private String name;

            private String alias;

            private String type;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            @Override
            public String toString() {
                return "Column{" +
                        "name='" + name + '\'' +
                        ", alias='" + alias + '\'' +
                        ", type='" + type + '\'' +
                        '}';
            }
        }

        public List<Column> getSchema() {
            return schema;
        }

        public void setSchema(List<Column> schema) {
            this.schema = schema;
        }

        public List<List<Object>> getDatarows() {
            return datarows;
        }

        public void setDatarows(List<List<Object>> datarows) {
            this.datarows = datarows;
        }

        @Override
        public String toString() {
            return "QueryResult{" +
                    "schema=" + schema +
                    ", datarows=" + datarows +
                    '}';
        }
    }

    private static class ErrorMessage {
        private Integer status;


        private ErrorBody error;

        private static class ErrorBody {
            private String reason;

            private String details;

            private String type;

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            @Override
            public String toString() {
                return "ErrorBody{" +
                        "reason='" + reason + '\'' +
                        ", details='" + details + '\'' +
                        ", type='" + type + '\'' +
                        '}';
            }
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public ErrorBody getError() {
            return error;
        }

        public void setError(ErrorBody error) {
            this.error = error;
        }

        @Override
        public String toString() {
            return "ErrorMessage{" +
                    "status=" + status +
                    ", error=" + error +
                    '}';
        }
    }
}
