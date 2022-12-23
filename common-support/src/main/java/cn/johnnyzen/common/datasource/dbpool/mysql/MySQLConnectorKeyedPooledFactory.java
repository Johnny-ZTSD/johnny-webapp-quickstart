package cn.johnnyzen.common.datasource.dbpool.mysql;

import cn.johnnyzen.common.datasource.entity.DataSource;
import cn.johnnyzen.common.datasource.connector.AbstractConnector;
import cn.johnnyzen.common.datasource.connector.mysql.MySQLConnector;
import cn.johnnyzen.common.datasource.dbpool.AbstractDatabaseConnectorKeyedPooledFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/12/2 11:57
 * @description ...
 */
public class MySQLConnectorKeyedPooledFactory extends AbstractDatabaseConnectorKeyedPooledFactory<DataSource, MySQLConnector> {
    private static final Logger logger = LoggerFactory.getLogger(MySQLConnectorKeyedPooledFactory.class);

    @Override
    public AbstractConnector<Connection> create(DataSource dataSource) throws Exception {
        MySQLConnector connector = new MySQLConnector(dataSource);
        connector.build();
        return connector;
    }
}
