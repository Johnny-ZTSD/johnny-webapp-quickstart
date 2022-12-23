package cn.seres.bd.dataservice.common.dbpool.clickhouse;

import cn.seres.bd.dataservice.common.connector.ClickhouseConnector;
import cn.seres.bd.dataservice.common.dbpool.AbstractDatabaseConnectorKeyedObjectPool;
import cn.seres.bd.dataservice.common.dbpool.AbstractDatabaseConnectorKeyedPooledFactory;
import cn.seres.bd.dataservice.model.datasource.DataSource;

/**
 * @author 408675 (tai.zeng@seres.cn)
 * @version v1.0
 * @project bdp_common_data_service
 * @create-time 2022/12/8 16:42
 * @description ...
 */
public class ClickhouseConnectorKeyedObjectPool extends AbstractDatabaseConnectorKeyedObjectPool<DataSource, ClickhouseConnector> {

    public ClickhouseConnectorKeyedObjectPool(AbstractDatabaseConnectorKeyedPooledFactory factory) {
        super(factory);
    }
}
