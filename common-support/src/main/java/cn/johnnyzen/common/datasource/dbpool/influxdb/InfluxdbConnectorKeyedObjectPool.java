package cn.seres.bd.dataservice.common.dbpool.influxdb;

import cn.seres.bd.dataservice.common.connector.InfluxDbConnector;
import cn.seres.bd.dataservice.common.dbpool.AbstractDatabaseConnectorKeyedObjectPool;
import cn.seres.bd.dataservice.common.dbpool.AbstractDatabaseConnectorKeyedPooledFactory;
import cn.seres.bd.dataservice.model.datasource.DataSource;

/**
 * @author 408675 (tai.zeng@seres.cn)
 * @version v1.0
 * @project bdp_common_data_service
 * @create-time 2022/12/8 16:48
 * @description ...
 */
public class InfluxdbConnectorKeyedObjectPool extends AbstractDatabaseConnectorKeyedObjectPool<DataSource, InfluxDbConnector> {
    public InfluxdbConnectorKeyedObjectPool(AbstractDatabaseConnectorKeyedPooledFactory factory) {
        super(factory);
    }
}
