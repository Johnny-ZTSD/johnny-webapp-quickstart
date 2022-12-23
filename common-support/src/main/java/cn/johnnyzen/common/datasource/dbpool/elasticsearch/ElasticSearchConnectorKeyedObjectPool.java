package cn.seres.bd.dataservice.common.dbpool.elasticsearch;

import cn.seres.bd.dataservice.common.connector.elasticsearch.ElasticSearchConnector;
import cn.seres.bd.dataservice.common.dbpool.AbstractDatabaseConnectorKeyedObjectPool;
import cn.seres.bd.dataservice.common.dbpool.AbstractDatabaseConnectorKeyedPooledFactory;
import cn.seres.bd.dataservice.model.datasource.DataSource;

/**
 * @author 408675 (tai.zeng@seres.cn)
 * @version v1.0
 * @project bdp_common_data_service
 * @create-time 2022/12/8 16:47
 * @description ...
 */
public class ElasticSearchConnectorKeyedObjectPool extends AbstractDatabaseConnectorKeyedObjectPool<DataSource, ElasticSearchConnector> {
    public ElasticSearchConnectorKeyedObjectPool(AbstractDatabaseConnectorKeyedPooledFactory factory) {
        super(factory);
    }
}
