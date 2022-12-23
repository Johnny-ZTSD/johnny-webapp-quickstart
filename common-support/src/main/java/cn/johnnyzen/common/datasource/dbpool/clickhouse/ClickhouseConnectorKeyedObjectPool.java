package cn.johnnyzen.common.datasource.dbpool.clickhouse;

import cn.johnnyzen.common.datasource.entity.DataSource;
import cn.johnnyzen.common.datasource.connector.clickhouse.ClickhouseConnector;
import cn.johnnyzen.common.datasource.dbpool.AbstractDatabaseConnectorKeyedObjectPool;
import cn.johnnyzen.common.datasource.dbpool.AbstractDatabaseConnectorKeyedPooledFactory;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/12/8 16:42
 * @description ...
 */
public class ClickhouseConnectorKeyedObjectPool extends AbstractDatabaseConnectorKeyedObjectPool<DataSource, ClickhouseConnector> {

    public ClickhouseConnectorKeyedObjectPool(AbstractDatabaseConnectorKeyedPooledFactory factory) {
        super(factory);
    }
}
