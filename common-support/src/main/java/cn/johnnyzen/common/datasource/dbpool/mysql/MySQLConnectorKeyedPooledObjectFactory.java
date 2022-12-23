package cn.seres.bd.dataservice.common.dbpool.mysql;

import cn.seres.bd.dataservice.common.connector.AbstractConnector;
import cn.seres.bd.dataservice.common.connector.InfluxDbConnector;
import cn.seres.bd.dataservice.common.connector.MySQLConnector;
import cn.seres.bd.dataservice.common.dbpool.AbstractDatabaseConnectorKeyedPooledFactory;
import cn.seres.bd.dataservice.model.datasource.DataSource;
import org.apache.commons.pool2.PooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 408675 (tai.zeng@seres.cn)
 * @version v1.0
 * @project bdp_common_data_service
 * @create-time 2022/12/2 11:55
 * @description ...
 */
public class MySQLConnectorKeyedPooledObjectFactory extends AbstractDatabaseConnectorKeyedPooledFactory<DataSource, MySQLConnector> {
    private static final Logger logger = LoggerFactory.getLogger(MySQLConnectorKeyedPooledObjectFactory.class);

    @Override
    public MySQLConnector create(DataSource dataSource) throws Exception {
        MySQLConnector connector = new MySQLConnector(dataSource);
        connector.build();
        return connector;
    }

    @Override
    public void destroyObject(DataSource dataSource, PooledObject<AbstractConnector> pooledObject) throws Exception {
        MySQLConnector connector = (MySQLConnector) pooledObject.getObject();
        connector.close();
        logger.debug("destroying a database connector object and connection ... and current instanceId : {}, datasourceId : {}, datasourceType2 : {}", connector.getInstanceId(), dataSource.getDatasourceId(), dataSource.getDatasourceType2());
    }

    @Override
    public boolean validateObject(DataSource dataSource, PooledObject<AbstractConnector> pooledObject) {
        MySQLConnector connector = (MySQLConnector) pooledObject.getObject();
        boolean health = connector.health();
        logger.debug("validating a database connector object and connection ...,and current health status is : {}, current instanceId : {}, datasourceId : {}, datasourceType2 : {}", health, connector.getInstanceId(), dataSource.getDatasourceId(), dataSource.getDatasourceType2());
        return health;
    }
}
