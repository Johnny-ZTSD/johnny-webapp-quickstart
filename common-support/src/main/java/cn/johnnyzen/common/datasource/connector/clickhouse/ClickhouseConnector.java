package cn.johnnyzen.common.datasource.connector.clickhouse;

import cn.johnnyzen.common.datasource.connector.AbstractConnector;
import cn.johnnyzen.common.exception.ApplicationRuntimeException;
import com.alibaba.nacos.common.utils.StringUtils;
import com.clickhouse.jdbc.ClickHouseDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Map;
import java.util.Properties;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-date 2022/8/18 11:06
 * @description
 * @reference-doc
 *  [1] 双重检查锁定（Double-Checked Locking）的问题和解决方案 - CSDN - https://blog.csdn.net/u013490280/article/details/108722926
 */
public class ClickhouseConnector extends AbstractConnector<Connection> {
    private static Logger logger = LoggerFactory.getLogger(ClickhouseConnector.class);

    private DataSource clickhouseDataSource = null;

    public ClickhouseConnector(){

    }

    public ClickhouseConnector(cn.johnnyzen.common.datasource.entity.DataSource dataSource){
        super(dataSource);
    }

    public ClickhouseConnector(String url, String username, String password) {
        super(new cn.johnnyzen.common.datasource.entity.DataSource(url, username, password));
        /* 单独在此处再反复 build()，是为了兼容在(2022/12/2)重构数据源代码以前的老接口 */
        build();
    }

    public ClickhouseConnector(String url, String username, String password, Properties properties) {
        super(new cn.johnnyzen.common.datasource.entity.DataSource(url, username, password, properties));
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return this.getConnection().prepareStatement(sql);
    }

    public Statement createStatement(){
        Statement statement = null;
        SQLException exception = null;
        try {
            statement = this.getConnection().createStatement();
        } catch (SQLException e) {
            exception = e;
        }
        if(statement==null){
            logger.error("Fail to create statement of clickhouse!" + this.toString(), exception);
        }
        return statement;
    }

    public DataSource getClickhouseDataSource() {
        return this.clickhouseDataSource;
    }

    protected synchronized DataSource createClickhouseDataSource(){
        if(clickhouseDataSource==null){
            try {
                String url = this.getDataSource().getDatasourceUrl();
                Properties properties = this.getDataSource().getOtherProperties();
                this.clickhouseDataSource = new ClickHouseDataSource(url, properties);
            } catch (SQLException exception) {
                exception.printStackTrace();
                logger.error("fail to get clickhouse jdbc datasource cause by `SQLException`!");
                logger.error(String.format("error info :%s", exception.toString()));
            }
        }
        return this.clickhouseDataSource;
    }

    @Override
    protected synchronized Connection createConnection() {
        if(this.connection==null){
            //创建并返回 java.sql.DataSource
            DataSource dataSource = this.createClickhouseDataSource();
            try {
                String username = this.getDataSource().getDatasourceUser();
                String password = this.getDataSource().getDatasourcePassword();
                logger.debug("current datasource info is : {}", this.getDataSource().toString());
                if(StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
                    this.connection = dataSource.getConnection(username, password);
                } else {
                    this.connection = dataSource.getConnection();
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
                logger.error("fail to get clickhouse jdbc connection because `SQLException`!db-connector-config:{}", this.toString());
                logger.error(String.format("error info :%s", exception.toString()));
            }
        }
        return this.connection;
    }

    @Override
    protected void registerDriver() {
        //本 connector 类 获取 connection 时，无需注册驱动类
        return ;
    }

    @Override
    public Object query(String querySql, Map<String, Object> params) throws ApplicationRuntimeException {
        return query(querySql);
    }

    @Override
    public ResultSet query(String querySql) throws ApplicationRuntimeException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.getPreparedStatement(querySql);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    @Override
    public boolean health() {
        return super.health();
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public String toString() {
        return "ClickhouseConnector{" +
                "clickhouseDataSource=" + clickhouseDataSource +
                ", instanceId=" + instanceId +
                ", isBuildedConnection=" + isBuildedConnection +
                ", dataSourceConfig=" + dataSourceConfig +
                '}';
    }
}
