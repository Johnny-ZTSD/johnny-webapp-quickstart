package cn.seres.bd.dataservice.common.dbpool;

import cn.seres.bd.dataservice.common.connector.AbstractConnector;
import cn.seres.bd.dataservice.common.connector.RedisConnector;
import cn.seres.bd.dataservice.model.datasource.BaseDataSource;
import cn.seres.bd.dataservice.model.datasource.DataSource;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 408675 (tai.zeng@seres.cn)
 * @version v1.0
 * @project bdp_common_data_service
 * @create-time 2022/12/2 10:36
 * @description DataSource as key, AbstractConnector as value
 */
public abstract class AbstractDatabaseConnectorKeyedPooledFactory<K extends DataSource, V extends AbstractConnector> extends BaseKeyedPooledObjectFactory<DataSource, AbstractConnector> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDatabaseConnectorKeyedPooledFactory.class);

    @Override
    public PooledObject<AbstractConnector> wrap(AbstractConnector connector) {
        return new DefaultPooledObject<AbstractConnector>(connector);
    }

    /**
     * @description
     *  若 connector 的实现类 重写了 AbstractConnector.close() 方法时，建议 本方法(AbstractDatabaseConnectorKeyedPooledFactory.destroyObject) 也重写
     * @param dataSource
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void destroyObject(DataSource dataSource, PooledObject<AbstractConnector> pooledObject) throws Exception {
        AbstractConnector connector = pooledObject.getObject();
        connector.close();
        logger.debug("destroying a database connector object and connection ... and current instanceId : {}, datasourceId : {}, datasourceType2 : {}", connector.getInstanceId(), dataSource.getDatasourceId(), dataSource.getDatasourceType2());
    }

    /**
     * @description
     *  若 connector 的实现类 重写了 AbstractConnector.health() 方法时，建议 本方法(AbstractDatabaseConnectorKeyedPooledFactory.validateObject) 也重写
     * @param dataSource
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public boolean validateObject(DataSource dataSource, PooledObject<AbstractConnector> pooledObject) {
        AbstractConnector connector = pooledObject.getObject();
        boolean health = connector.health();
        logger.debug("validating a database connector object and connection ...,and current health status is : {}, current instanceId : {}, datasourceId : {}, datasourceType2 : {}", health, connector.getInstanceId(), dataSource.getDatasourceId(), dataSource.getDatasourceType2());
        return health;
    }
}
