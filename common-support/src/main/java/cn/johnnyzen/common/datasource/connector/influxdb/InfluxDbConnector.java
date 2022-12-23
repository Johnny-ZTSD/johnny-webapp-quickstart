//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.johnnyzen.common.datasource.connector.influxdb;

import cn.johnnyzen.common.datasource.connector.AbstractConnector;
import cn.johnnyzen.common.datasource.DataSource;
import cn.johnnyzen.common.exception.ApplicationErrorCodeEnum;
import cn.johnnyzen.common.exception.ApplicationRuntimeException;
import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.influxdb.dto.BatchPoints.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @project bdp-data-service-parent
 * @author 408675 (tai.zeng@seres.cn)
 * @create-date 2022/7/12 16:27
 * @version v1.0
 * @description ...
 */

public class InfluxDbConnector extends AbstractConnector<InfluxDB> {
    private static Logger logger = LoggerFactory.getLogger(InfluxDbConnector.class);

//    private InfluxDB connection;

    public InfluxDbConnector(String url, String username, String password, String database, String retentionPolicy) {
        super(new DataSource(null, url, username, password, database).setRetentionPolicy(retentionPolicy));
    }

    public InfluxDbConnector(String url, String username, String password, String database) {
        super(new DataSource(null, url, username, password, database));
    }

    public InfluxDbConnector(String url, String username, String password) {
        super(new DataSource(url, username, password));
    }

    public InfluxDbConnector(DataSource dataSource){
        super(dataSource);
    }

    @Override
    protected void registerDriver() {
        //本 connector 类 获取 connection 时，无需注册驱动类
        return ;
    }

    @Override
    protected synchronized InfluxDB createConnection() {
        if(this.connection==null){
            String url = this.dataSourceConfig.getDatasourceUrl();
            String username = this.dataSourceConfig.getDatasourceUser();
            String password = this.dataSourceConfig.getDatasourcePassword();
            String database = this.dataSourceConfig.getDatabase();
            String retentionPolicy = this.dataSourceConfig.getRetentionPolicy();
            logger.debug("current datasource info is : {}", this.getDataSource().toString());
            OkHttpClient.Builder client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .retryOnConnectionFailure(true);
            if(!ObjectUtils.isEmpty(retentionPolicy)){
                this.connection.setRetentionPolicy(retentionPolicy);
            }
            //this.connection = InfluxDBFactory.connect(this.url, this.username, this.password);
            InfluxDB connection = InfluxDBFactory.connect(url, username, password, client);
            this.connection = connection;
            if(database != null){
                this.connection.createDatabase(database);
            }
        }
        return this.connection;
    }

    public void insertBatch(String targetDatabase, Point[] points) {
        InfluxDB connection = this.getConnection();
        Builder builder = BatchPoints.database(targetDatabase);
        builder.points(points);
        connection.write(builder.build());
    }

    public QueryResult query(String querySql, String dbName) throws ApplicationRuntimeException {
        Map<String, Object> params = new HashMap<>();
        params.put(DataSource.PARAM_DATABASE, dbName);
        return query(querySql, params);
    }

    @Override
    public QueryResult query(String querySql, Map<String, Object> params) throws ApplicationRuntimeException {
        QueryResult queryResult = null;
        if (StringUtils.isEmpty(querySql)) {
            throw new ApplicationRuntimeException(ApplicationErrorCodeEnum.QUERY_SQL_IS_EMPTY);
        } else {
            InfluxDB connection = this.getConnection();
            String database = (String) params.get(DataSource.PARAM_DATABASE);
            database = (database!=null)?database:this.getDataSource().getDatabase();
            if(!ObjectUtils.isEmpty(database)){
                queryResult = connection.query(new Query(querySql, database));
            } else {
                queryResult = connection.query(new Query(querySql));
            }
        }
        return queryResult;
    }

    @Override
    public QueryResult query(String querySql) throws ApplicationRuntimeException {
        if (StringUtils.isEmpty(querySql)) {
            throw new ApplicationRuntimeException(ApplicationErrorCodeEnum.QUERY_SQL_IS_EMPTY);
        } else {
            InfluxDB connection = this.getConnection();
            return connection.query(new Query(querySql));
        }
    }

    /**
     * 删除
     * @param deleteSql 删除语句
     *   eg: String command = "delete from sys_code where TAG_CODE='ABC'";
     * @return 返回错误信息
     * @reference-doc
     *  [1] InfluxDB -JAVA增删查 - CSDN - https://blog.csdn.net/daxiong0816/article/details/116267778
     */
    public String delete(String deleteSql){
        String database = this.getDataSource().getDatabase();
        InfluxDB connection = this.getConnection();
        QueryResult result = connection.query(new Query(deleteSql, database));
        return result.getError();
    }

    /**
     * 删除数据库
     * @param dbName
     */
    public void dropDatabase(String dbName){
        InfluxDB connection = this.getConnection();
        connection.deleteDatabase(dbName);
        logger.warn("success to drop database: {}", dbName);
    }


    @Override
    public void close() {
        InfluxDB connection = this.getConnection();
        if(connection != null){
            connection.close();
            this.connection = null;
        }
    }

    @Override
    public boolean health() {
        InfluxDB connection = this.getConnection();
        Pong ping = connection.ping();
        return ping.isGood();
    }

    @Override
    public String toString() {
        return "InfluxDbConnector{" +
                "instanceId=" + instanceId +
                ", isBuildedConnection=" + isBuildedConnection +
                ", dataSourceConfig=" + dataSourceConfig +
                '}';
    }
}
