package cn.seres.bd.dataservice.common.dbpool.elasticsearch;


import cn.seres.bd.dataservice.common.connector.ClickhouseConnector;
import cn.seres.bd.dataservice.common.connector.elasticsearch.ElasticSearchConnector;
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
public class ElasticSearchConnectorPool extends GenericObjectPool<ElasticSearchConnector> {
    public ElasticSearchConnectorPool(PooledObjectFactory<ElasticSearchConnector> factory) {
        super(factory);
    }

    public ElasticSearchConnectorPool(PooledObjectFactory<ElasticSearchConnector> factory, GenericObjectPoolConfig<ElasticSearchConnector> config) {
        super(factory, config);
    }

    public ElasticSearchConnectorPool(PooledObjectFactory<ElasticSearchConnector> factory, GenericObjectPoolConfig<ElasticSearchConnector> config, AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }
}