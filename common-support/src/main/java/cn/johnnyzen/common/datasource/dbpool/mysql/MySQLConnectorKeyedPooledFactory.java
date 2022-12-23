package cn.seres.bd.dataservice.common.dbpool.mysql;

import cn.seres.bd.dataservice.common.connector.AbstractConnector;
import cn.seres.bd.dataservice.common.connector.MySQLConnector;
import cn.seres.bd.dataservice.common.dbpool.AbstractDatabaseConnectorKeyedPooledFactory;
import cn.seres.bd.dataservice.common.dbpool.redis.RedisConnectorKeyedPooledObjectFactory;
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
 * @create-time 2022/12/2 11:57
 * @description ...
 */
public class MySQLConnectorKeyedPooledFactory extends AbstractDatabaseConnectorKeyedPooledFactory<DataSource, MySQLConnector> {
    private static final Logger logger = LoggerFactory.getLogger(MySQLConnectorKeyedPooledFactory.class);

    @Override
    public AbstractConnector<Connection> create(DataSource dataSource) throws Exception {
        MySQLConnector connector = new MySQLConnector(dataSource);
        connector.build();
        return connector;
    }
}
