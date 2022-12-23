package cn.johnnyzen.common.datasource.dbpool.mysql;

import cn.johnnyzen.common.datasource.entity.DataSource;
import cn.johnnyzen.common.datasource.connector.mysql.MySQLConnector;
import cn.johnnyzen.common.datasource.dbpool.AbstractDatabaseConnectorKeyedObjectPool;
import cn.johnnyzen.common.datasource.dbpool.AbstractDatabaseConnectorKeyedPooledFactory;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/12/8 16:49
 * @description ...
 */
public class MySQLConnectorKeyedObjectPool extends AbstractDatabaseConnectorKeyedObjectPool<DataSource, MySQLConnector> {
    public MySQLConnectorKeyedObjectPool(AbstractDatabaseConnectorKeyedPooledFactory factory) {
        super(factory);
    }
}
