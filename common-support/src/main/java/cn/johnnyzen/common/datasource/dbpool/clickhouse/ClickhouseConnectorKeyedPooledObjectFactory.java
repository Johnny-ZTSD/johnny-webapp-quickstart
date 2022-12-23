package cn.seres.bd.dataservice.common.dbpool.clickhouse;

import cn.seres.bd.dataservice.common.connector.AbstractConnector;
import cn.seres.bd.dataservice.common.connector.ClickhouseConnector;
import cn.seres.bd.dataservice.common.connector.elasticsearch.ElasticSearchConnector;
import cn.seres.bd.dataservice.common.dbpool.AbstractDatabaseConnectorKeyedPooledFactory;
import cn.seres.bd.dataservice.common.dbpool.elasticsearch.ElasticSearchConnectorKeyedPooledObjectFactory;
import cn.seres.bd.dataservice.model.datasource.DataSource;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * @author 408675 (tai.zeng@seres.cn)
 * @version v1.0
 * @project bdp_common_data_service
 * @create-time 2022/12/2 11:46
 * @description ...
 */
public class ClickhouseConnectorKeyedPooledObjectFactory extends AbstractDatabaseConnectorKeyedPooledFactory<DataSource, ClickhouseConnector> {
    private static final Logger logger = LoggerFactory.getLogger(ClickhouseConnectorKeyedPooledObjectFactory.class);
    @Override
    public AbstractConnector<Connection> create(DataSource dataSource) throws Exception {
        ClickhouseConnector connector = new ClickhouseConnector(dataSource);
        connector.build();
        return connector;
    }

    @Override
    public void destroyObject(DataSource dataSource, PooledObject<AbstractConnector> pooledObject) throws Exception {
        ClickhouseConnector connector = (ClickhouseConnector) pooledObject.getObject();
        connector.close();
        logger.debug("destroying a database connector object and connection ... and current instanceId : {}, datasourceId : {}, datasourceType2 : {}", connector.getInstanceId(), dataSource.getDatasourceId(), dataSource.getDatasourceType2());
    }

    @Override
    public boolean validateObject(DataSource dataSource, PooledObject<AbstractConnector> pooledObject) {
        ClickhouseConnector connector = (ClickhouseConnector) pooledObject.getObject();
        boolean health = connector.health();
        logger.debug("validating a database connector object and connection ...,and current health status is : {}, current instanceId : {}, datasourceId : {}, datasourceType2 : {}", health, connector.getInstanceId(), dataSource.getDatasourceId(), dataSource.getDatasourceType2());
        return health;
    }
}
