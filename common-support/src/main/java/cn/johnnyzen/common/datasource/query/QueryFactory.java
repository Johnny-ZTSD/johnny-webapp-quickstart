package cn.johnnyzen.common.datasource.query;

import cn.johnnyzen.common.datasource.connector.AbstractConnector;
import cn.johnnyzen.common.datasource.datatype.DatasourceCatagory;
import cn.johnnyzen.common.datasource.datatype.DatasourceType;
import cn.seres.bd.dataservice.common.connector.*;
import cn.seres.bd.dataservice.common.connector.elasticsearch.ElasticSearchConnector;
import cn.seres.bd.dataservice.common.dto.page.PageResponse;
import cn.seres.bd.dataservice.common.emums.DsType1;
import cn.seres.bd.dataservice.common.emums.DsType2;
import cn.seres.bd.dataservice.common.exception.BusinessException;
import cn.seres.bd.dataservice.common.exception.CommonErrEnum;
import cn.seres.bd.dataservice.common.exception.CommonException;
import cn.seres.bd.dataservice.model.entity.QueryJobInfo;
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
        if (DsType1.DATABASE.equals(datasourceType) && dbType != null) {
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
                    throw new BusinessException("`MySQLQuery` class is need platform development to implements!");
                case REDIS:
                    querier = new RedisQuery((RedisConnector) connector);
                    break;
                default:
                    break;
            }
        } else {
            logger.error(CommonErrEnum.QUERY_NOT_SUPPORT.getCode());
            throw new BusinessException("errorCode: %s, errorMessage: %s", CommonErrEnum.QUERY_NOT_SUPPORT.getCode(), CommonErrEnum.QUERY_NOT_SUPPORT.getMsg());
        }
        return querier;
    }

    public static PageResponse query(QueryJobInfo queryJobInfo, Map<String, Object> params, Boolean isRequestNeedPaging, AbstractQuery querier){
        PageResponse result = null;
        if (isRequestNeedPaging && queryJobInfo.getIsSqlSupportAutoPaging()) { // step3.1 需要分页 且 支持自动分页查询
            try {
                result = querier.autoPagingQuery(queryJobInfo, params);
            } catch (CommonException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                logger.error("Fail to query dataset of datasource by paging!(queryJobInfo:{})", queryJobInfo);
                logger.error("SQLException's detail information is as follows: {}", e);
                throw new RuntimeException(e);
            }
        } else { // step3.2 无需分页 或 需要分页但只能手动分页(定制化分页/伪分页) [但: 此处只是查库，若需分页/手动分页，放在后置处理器进行]
            try {
                result = querier.query(queryJobInfo, params);
            } catch (CommonException e) {
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
