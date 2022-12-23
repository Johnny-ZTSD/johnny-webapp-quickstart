package cn.johnnyzen.common.datasource.dbpool.redis;

import cn.johnnyzen.common.datasource.entity.DataSource;
import cn.johnnyzen.common.datasource.connector.redis.RedisConnector;
import cn.johnnyzen.common.datasource.dbpool.AbstractDatabaseConnectorKeyedObjectPool;
import cn.johnnyzen.common.datasource.dbpool.AbstractDatabaseConnectorKeyedPooledFactory;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/12/8 16:49
 * @description ...
 */
public class RedisConnectorKeyedObjectPool extends AbstractDatabaseConnectorKeyedObjectPool<DataSource, RedisConnector> {
    public RedisConnectorKeyedObjectPool(AbstractDatabaseConnectorKeyedPooledFactory factory) {
        super(factory);
    }
}
