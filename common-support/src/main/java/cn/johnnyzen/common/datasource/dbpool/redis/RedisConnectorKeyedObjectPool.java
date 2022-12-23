package cn.seres.bd.dataservice.common.dbpool.redis;

import cn.seres.bd.dataservice.common.connector.RedisConnector;
import cn.seres.bd.dataservice.common.dbpool.AbstractDatabaseConnectorKeyedObjectPool;
import cn.seres.bd.dataservice.common.dbpool.AbstractDatabaseConnectorKeyedPooledFactory;
import cn.seres.bd.dataservice.model.datasource.DataSource;

/**
 * @author 408675 (tai.zeng@seres.cn)
 * @version v1.0
 * @project bdp_common_data_service
 * @create-time 2022/12/8 16:49
 * @description ...
 */
public class RedisConnectorKeyedObjectPool extends AbstractDatabaseConnectorKeyedObjectPool<DataSource, RedisConnector> {
    public RedisConnectorKeyedObjectPool(AbstractDatabaseConnectorKeyedPooledFactory factory) {
        super(factory);
    }
}
