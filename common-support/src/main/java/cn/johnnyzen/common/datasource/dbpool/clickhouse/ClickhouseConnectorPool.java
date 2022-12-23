package cn.seres.bd.dataservice.common.dbpool.clickhouse;


import cn.seres.bd.dataservice.common.connector.ClickhouseConnector;
import cn.seres.bd.dataservice.common.connector.InfluxDbConnector;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author 408675 (tai.zeng@seres.cn) / Jenson
 * @version v1.0
 * @project bdp_common_data_service
 * @create-time 2022/12/1 15:05
 * @description
 * @reference-doc
 *  [1] java 对象池 commons-pool2 的使用(创建influxDB连接池) - jianshu - https://www.jianshu.com/p/46c27ee9e110
 */
public class ClickhouseConnectorPool extends GenericObjectPool<ClickhouseConnector> {
    public ClickhouseConnectorPool(PooledObjectFactory<ClickhouseConnector> factory) {
        super(factory);
    }

    public ClickhouseConnectorPool(PooledObjectFactory<ClickhouseConnector> factory, GenericObjectPoolConfig<ClickhouseConnector> config) {
        super(factory, config);
    }

    public ClickhouseConnectorPool(PooledObjectFactory<ClickhouseConnector> factory, GenericObjectPoolConfig<ClickhouseConnector> config, AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }
}