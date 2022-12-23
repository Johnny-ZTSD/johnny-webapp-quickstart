package cn.johnnyzen.common.datasource.dbpool.elasticsearch;

import cn.johnnyzen.common.datasource.entity.DataSource;
import cn.johnnyzen.common.datasource.connector.elasticsearch.ElasticSearchConnector;
import cn.johnnyzen.common.datasource.dbpool.AbstractDatabaseConnectorKeyedObjectPool;
import cn.johnnyzen.common.datasource.dbpool.AbstractDatabaseConnectorKeyedPooledFactory;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/12/8 16:47
 * @description ...
 */
public class ElasticSearchConnectorKeyedObjectPool extends AbstractDatabaseConnectorKeyedObjectPool<DataSource, ElasticSearchConnector> {
    public ElasticSearchConnectorKeyedObjectPool(AbstractDatabaseConnectorKeyedPooledFactory factory) {
        super(factory);
    }
}
