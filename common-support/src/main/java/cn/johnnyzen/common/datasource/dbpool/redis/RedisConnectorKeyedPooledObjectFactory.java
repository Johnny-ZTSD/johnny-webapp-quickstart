package cn.johnnyzen.common.datasource.dbpool.redis;

import cn.johnnyzen.common.datasource.entity.DataSource;
import cn.johnnyzen.common.datasource.connector.AbstractConnector;
import cn.johnnyzen.common.datasource.connector.redis.RedisConnector;
import cn.johnnyzen.common.datasource.dbpool.AbstractDatabaseConnectorKeyedPooledFactory;

import org.apache.commons.pool2.PooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/12/2 11:46
 * @description ...
 */
public class RedisConnectorKeyedPooledObjectFactory extends AbstractDatabaseConnectorKeyedPooledFactory<DataSource, RedisConnector> {
    private static final Logger logger = LoggerFactory.getLogger(RedisConnectorKeyedPooledObjectFactory.class);

    @Override
    public RedisConnector create(DataSource dataSource) throws Exception {
        RedisConnector connector = new RedisConnector(dataSource);
        connector.build();
        return connector;
    }

    @Override
    public void destroyObject(DataSource dataSource, PooledObject<AbstractConnector> pooledObject) throws Exception {
        RedisConnector connector = (RedisConnector) pooledObject.getObject();
        connector.close();
        logger.debug("destroying a database connector object and connection ... and current instanceId : {}, datasourceId : {}, datasourceType2 : {}", connector.getInstanceId(), dataSource.getDatasourceId(), dataSource.getDatasourceType());
    }

    @Override
    public boolean validateObject(DataSource dataSource, PooledObject<AbstractConnector> pooledObject) {
        RedisConnector connector = (RedisConnector) pooledObject.getObject();
        boolean health = connector.health();
        logger.debug("validating a database connector object and connection ...,and current health status is : {}, current instanceId : {}, datasourceId : {}, datasourceType2 : {}", health, connector.getInstanceId(), dataSource.getDatasourceId(), dataSource.getDatasourceType());
        return health;
    }
}
