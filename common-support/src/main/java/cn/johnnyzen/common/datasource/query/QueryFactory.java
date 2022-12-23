package cn.johnnyzen.common.datasource.query;

import cn.johnnyzen.common.datasource.connector.AbstractConnector;
import cn.johnnyzen.common.datasource.connector.clickhouse.ClickhouseConnector;
import cn.johnnyzen.common.datasource.connector.elasticsearch.ElasticSearchConnector;
import cn.johnnyzen.common.datasource.connector.influxdb.InfluxDbConnector;
import cn.johnnyzen.common.datasource.connector.mysql.MySQLConnector;
import cn.johnnyzen.common.datasource.connector.redis.RedisConnector;
import cn.johnnyzen.common.datasource.datatype.DatasourceCatagory;
import cn.johnnyzen.common.datasource.datatype.DatasourceType;
import cn.johnnyzen.common.datasource.entity.QueryJobInfo;
import cn.johnnyzen.common.datasource.query.clickhouse.ClickhouseQuery;
import cn.johnnyzen.common.datasource.query.elasticsearch.ElasticSearchQuery;
import cn.johnnyzen.common.datasource.query.influxdb.InfluxDBQuery;
import cn.johnnyzen.common.datasource.query.mysql.MySQLQuery;
import cn.johnnyzen.common.datasource.query.redis.RedisQuery;
import cn.johnnyzen.common.dto.ResponseCodeEnum;
import cn.johnnyzen.common.dto.page.PageResponse;
import cn.johnnyzen.common.exception.ApplicationRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.sql.SQLException;
import java.util.Map;


/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/11/15 0:37
 * @description 简单工厂模式
 */
public class QueryFactory {
    private final static Logger logger = LoggerFactory.getLogger(QueryFactory.class);

    /**
     *
     * @param datasourceType
     * @param dbType
     * @param connector
     *  1、允许 connector 为空.因为目前存在个别数据源(ElasticSearch)暂不依赖于 基于 JDBC 实现的 ElasticSearchConnector( extends AbstractConnector)
     * @return
     */
    public static AbstractQuery getQuerier(DatasourceCatagory datasourceType, DatasourceType dbType, AbstractConnector connector){
        if(ObjectUtils.isEmpty(connector)){
            String warnMessage = String.format("connector is empty when getQuerier(...), datasourceType: %s, dbType: %s, !", datasourceType, dbType);
            logger.warn(warnMessage);
//            throw new BusinessException(errorMessage);
        }
        AbstractQuery querier = null;
        if (DatasourceCatagory.DATABASE.equals(datasourceType) && dbType != null) {
            switch (dbType) {
                case INFLUXDB:
                    querier = new InfluxDBQuery((InfluxDbConnector) connector);
                    break;
                case CLICKHOUSE:
                    querier = new ClickhouseQuery((ClickhouseConnector) connector);
                    break;
                case ELASTICSEARCH:
                    querier = new ElasticSearchQuery((ElasticSearchConnector) connector);
                    break;
                case MYSQL:
                    querier = new MySQLQuery((MySQLConnector) connector);
                    //TODO MYSQL数据源适配
                    throw new ApplicationRuntimeException("`MySQLQuery` class is need platform development to implements!");
                case REDIS:
                    querier = new RedisQuery((RedisConnector) connector);
                    break;
                default:
                    break;
            }
        } else {
            logger.error(ResponseCodeEnum.DATABASE_TYPE_NOT_SUPPORT.toString());
            throw new ApplicationRuntimeException("errorCode: %s, errorMessage: %s", ResponseCodeEnum.DATABASE_TYPE_NOT_SUPPORT.getCode(), ResponseCodeEnum.DATABASE_TYPE_NOT_SUPPORT.getName());
        }
        return querier;
    }

    public static PageResponse query(QueryJobInfo queryJobInfo, Map<String, Object> params, Boolean isRequestNeedPaging, AbstractQuery querier){
        PageResponse result = null;
        if (isRequestNeedPaging && queryJobInfo.getSqlSupportAutoPagable()) { // step3.1 需要分页 且 支持自动分页查询
            try {
                result = querier.autoPagingQuery(queryJobInfo, params);
            } catch (ApplicationRuntimeException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                logger.error("Fail to query dataset of datasource by paging!(queryJobInfo:{})", queryJobInfo);
                logger.error("SQLException's detail information is as follows: {}", e);
                throw new RuntimeException(e);
            }
        } else { // step3.2 无需分页 或 需要分页但只能手动分页(定制化分页/伪分页) [但: 此处只是查库，若需分页/手动分页，放在后置处理器进行]
            try {
                result = querier.query(queryJobInfo, params);
            } catch (ApplicationRuntimeException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                logger.error("Fail to query dataset of datasource by no-paging!(queryJobInfo:{})", queryJobInfo);
                logger.error("SQLException's detail information is as follows: {}", e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}
