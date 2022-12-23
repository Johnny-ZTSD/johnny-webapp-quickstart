//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.johnnyzen.common.datasource.connector.mysql;

import java.sql.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.johnnyzen.common.datasource.connector.AbstractConnector;
import cn.johnnyzen.common.datasource.entity.DataSource;
import cn.johnnyzen.common.dto.ResponseCodeEnum;
import cn.johnnyzen.common.exception.ApplicationRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

/**
 * @author johnnyzen
 * @create-date 2022/7/12 16:27
 * @version v1.0
 * @description
 *  [1] JDBC_DRIVER :
 *      "com.mysql.cj.jdbc.Driver"
 *      ...
 */

public class MySQLConnector extends AbstractConnector<Connection> {
    private static Logger logger = LoggerFactory.getLogger(MySQLConnector.class);
//    private Connection connection = null;

    private static final String DEFAULT_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * 已注册的驱动
     * @description 避免重复注册，耗费性能
     */
    private static Set<String> REGISTERED_JDBC_DRIVERS = new HashSet<>();

    public MySQLConnector(){

    }

    public MySQLConnector(DataSource dataSource){
        super(dataSource);
    }

    public MySQLConnector(String url, String username, String password) {
        super(new DataSource(DEFAULT_JDBC_DRIVER, url, username, password));
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return this.getConnection().prepareStatement(sql);
    }

    @Override
    protected synchronized Connection createConnection() {
        this.registerDriver();
        if (this.connection == null) {
            try {
                String url = this.getDataSource().getDatasourceUrl();
                String username = this.getDataSource().getDatasourceUser();
                String password = this.getDataSource().getDatasourcePassword();
                logger.debug("current datasource info is : {}", this.getDataSource().toString());
                this.connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException exception) {
                logger.error("fail to register mysql jdbc connection because `SQLException`!");
                logger.error(String.format("error info :%s", exception.toString()));
                throw new RuntimeException(exception);
            }
        }
        return this.connection;
    }

    @Override
    protected void registerDriver() {
        String driverClass = this.getDataSource().getDatasourceDriverClass();
        if(REGISTERED_JDBC_DRIVERS.contains(driverClass)==false){
            String driver = this.getDataSource().getDatasourceDriverClass();
            driver = (driver == null)?DEFAULT_JDBC_DRIVER:driver;
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException exception) {
                logger.error("fail to register mysql jdbc driver because `ClassNotFoundException`!");
                logger.error(String.format("error info :%s", exception.toString()));
                throw new RuntimeException(exception);
            }
            logger.info("success to register mysql jdbc driver!");
            REGISTERED_JDBC_DRIVERS.add(driverClass);
        }
    }

    @Override
    public Object query(String querySql, Map<String, Object> params) throws ApplicationRuntimeException {
        return query(querySql);
    }

    @Override
    public ResultSet query(String querySql) throws ApplicationRuntimeException {
        try {
            return this.getPreparedStatement(querySql).executeQuery();
        } catch (SQLException e) {
            logger.error("Fail to query data from mysql! querySql is: {}, Exception information as follows: {}", querySql, e);
            throw new ApplicationRuntimeException(ResponseCodeEnum.DATABASE_QUERY_ERROR.toString());
        }
    }

    @Override
    public boolean health() {
        return super.health();
    }

    @Override
    public void close() {
        super.close();
    }

    public void close(PreparedStatement preparedStatement) {
        if(!ObjectUtils.isEmpty(preparedStatement)){
            try {
                preparedStatement.close();
                this.close();
            } catch (SQLException exception) {
                logger.error(exception.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        return "MysqlConnector{" +
                "instanceId=" + instanceId +
                ", isBuildedConnection=" + isBuildedConnection +
                ", dataSourceConfig=" + dataSourceConfig +
                '}';
    }
}
