package cn.johnnyzen.common.datasource.dbpool.influxdb;

import cn.johnnyzen.common.datasource.entity.DataSource;
import cn.johnnyzen.common.datasource.connector.influxdb.InfluxDbConnector;
import cn.johnnyzen.common.datasource.dbpool.AbstractDatabaseConnectorKeyedObjectPool;
import cn.johnnyzen.common.datasource.dbpool.AbstractDatabaseConnectorKeyedPooledFactory;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/12/8 16:48
 * @description ...
 */
public class InfluxdbConnectorKeyedObjectPool extends AbstractDatabaseConnectorKeyedObjectPool<DataSource, InfluxDbConnector> {
    public InfluxdbConnectorKeyedObjectPool(AbstractDatabaseConnectorKeyedPooledFactory factory) {
        super(factory);
    }
}
