package cn.johnnyzen.common.datasource.dbpool.elasticsearch;

import cn.johnnyzen.common.datasource.entity.DataSource;
import cn.johnnyzen.common.datasource.connector.AbstractConnector;
import cn.johnnyzen.common.datasource.connector.elasticsearch.ElasticSearchConnector;
import cn.johnnyzen.common.datasource.dbpool.AbstractDatabaseConnectorKeyedPooledFactory;
import org.apache.commons.pool2.PooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/12/2 11:52
 * @description ...
 */
public class ElasticSearchConnectorKeyedPooledObjectFactory extends AbstractDatabaseConnectorKeyedPooledFactory<DataSource, ElasticSearchConnector> {
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConnectorKeyedPooledObjectFactory.class);

    @Override
    public AbstractConnector<Connection> create(DataSource dataSource) throws Exception {
        ElasticSearchConnector connector = new ElasticSearchConnector(dataSource);
        connector.build();
        return connector;
    }

    @Override
    public void destroyObject(DataSource dataSource, PooledObject<AbstractConnector> pooledObject) throws Exception {
        ElasticSearchConnector connector = (ElasticSearchConnector) pooledObject.getObject();
        connector.close();
        logger.debug("destroying a database connector object and connection ... and current instanceId : {}, datasourceId : {}, datasourceType2 : {}", connector.getInstanceId(), dataSource.getDatasourceId(), dataSource.getDatasourceType());
    }

    @Override
    public boolean validateObject(DataSource dataSource, PooledObject<AbstractConnector> pooledObject) {
        ElasticSearchConnector connector = (ElasticSearchConnector) pooledObject.getObject();
        boolean health = connector.health();
        logger.debug("validating a database connector object and connection ...,and current health status is : {}, current instanceId : {}, datasourceId : {}, datasourceType2 : {}", health, connector.getInstanceId(), dataSource.getDatasourceId(), dataSource.getDatasourceType());
        return health;
    }
}
