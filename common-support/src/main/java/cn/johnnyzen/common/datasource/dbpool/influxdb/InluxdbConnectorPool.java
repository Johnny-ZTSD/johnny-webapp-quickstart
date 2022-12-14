package cn.johnnyzen.common.datasource.dbpool.influxdb;


import cn.johnnyzen.common.datasource.connector.influxdb.InfluxDbConnector;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author johnnyzen / Jenson
 * @version v1.0
 * @create-time 2022/12/1 15:05
 * @description
 * @reference-doc
 *  [1] java 对象池 commons-pool2 的使用(创建influxDB连接池) - jianshu - https://www.jianshu.com/p/46c27ee9e110
 */
public class InluxdbConnectorPool extends GenericObjectPool<InfluxDbConnector> {
    public InluxdbConnectorPool(PooledObjectFactory<InfluxDbConnector> factory) {
        super(factory);
    }

    public InluxdbConnectorPool(PooledObjectFactory<InfluxDbConnector> factory, GenericObjectPoolConfig<InfluxDbConnector> config) {
        super(factory, config);
    }

    public InluxdbConnectorPool(PooledObjectFactory<InfluxDbConnector> factory, GenericObjectPoolConfig<InfluxDbConnector> config, AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }
}