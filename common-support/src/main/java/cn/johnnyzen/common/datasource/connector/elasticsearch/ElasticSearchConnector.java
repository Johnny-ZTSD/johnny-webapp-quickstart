package cn.johnnyzen.common.datasource.connector.elasticsearch;

import cn.johnnyzen.common.datasource.connector.AbstractConnector;
import cn.johnnyzen.common.datasource.DataSource;
import cn.johnnyzen.common.exception.ApplicationErrorCodeEnum;
import cn.johnnyzen.common.exception.ApplicationRuntimeException;
import cn.johnnyzen.common.util.io.net.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.sql.*;
import java.util.Map;

/**
 * @author johnnyzen
 * @date 2022/10/21 下午4:49
 * @description 1个 Connector 对象 即 一个 connection 连接实例
 * @reference-doc
 *  [1] https://github.com/opendistro-for-elasticsearch/sql/tree/main/sql-jdbc
 *  [2] https://github.com/jprante/elasticsearch-jdbc
 */
public class ElasticSearchConnector extends AbstractConnector<Connection> {
    private static Logger logger = LoggerFactory.getLogger(ElasticSearchConnector.class);

    //private Connection connection = null;

    public ElasticSearchConnector(DataSource dataSource) {
        super(dataSource);
    }

    public ElasticSearchConnector(String url, String username, String password) {
        super(new DataSource(url, username, password));
    }

    @Override
    protected synchronized Connection createConnection() {
        if (this.connection == null) {
            try {
                String jdbcUrl = this.convertUrl();
                String username = this.getDataSource().getDatasourceUser();
                String password = this.getDataSource().getDatasourcePassword();
                logger.debug("current datasource info is : {}", this.getDataSource().toString());
                this.connection = DriverManager.getConnection(jdbcUrl, username, password);
            } catch (SQLException exception) {
                exception.printStackTrace();
                logger.error("fail to get elasticsearch jdbc connection because `SQLException`!");
                logger.error(String.format("error info :%s", exception.toString()));
            }
        }
        return this.connection;
    }

    /**
     * 转换/解析 url
     * @descrption
     *  解析 url
     *      input-param: httpUrl
     *          eg: "http://10.37.18.178:9200/_opendistro/_sql?format=json"
     *          [httpUrl] http://10.37.18.178:9200/_opendistro/_sql [√] (数据库中配置的 HTTP URL, 即 dbConfigUrl)
     *              1、依赖于: HTTP 协议 （ElasticSearchQuery#post）
     *              2、依赖于 opendistro 插件
     *              3、要 完整的 JSON 结构，必须选 HTTP 方式
     *      output-result: jdbcUrl
     *          eg: "jdbc:elasticsearch://http://10.37.19.116:9200"
     *          [jdbcUrl] jdbc:elasticsearch://http://10.37.18.178:9200 => ElasticSearchConnector [X]
     * 	            1、依赖于: JDBC 协议（ElasticSearchConnector#createConnection）
     * 	            2、依赖于 opendistro 插件
     * 	            3、format: jdbc:elasticsearch://[scheme://][host][:port][/context-path]?[property-key=value]&[property-key2=value2]..&[property-keyN=valueN]
     * 	              sample: "jdbc:elasticsearch://https://remote-host-name?auth=aws_sigv4&region=us-west-1";
     * @return String:jdbcUrl
     * @reference-doc
     *  [1] https://github.com/opendistro-for-elasticsearch/sql/tree/main/sql-jdbc
     */
    public String convertUrl(){
        //数据库配置的 Url 即 httpUrl
        String httpUrl = this.getDataSource().getDatasourceUrl();
        Map<String, String> urlInfoMap = null;
        try {
            urlInfoMap = UrlUtil.parseUrl(httpUrl);
        } catch (IOException e) {
            logger.error("fail to parse url(httpUrl,the right-format is :`jdbc:elasticsearch://[scheme://][host][:port]?[property-key=value]&[property-key2=value2]..&[property-keyN=valueN]`) from database! current httpUrl value is {}", httpUrl);
            throw new RuntimeException(e);
        }
        StringBuilder jdbcUrl = new StringBuilder();
        jdbcUrl.append("jdbc:elasticsearch://");
        String protocol = urlInfoMap.get(UrlUtil.PARAM_PROTOCOL);
        if(!ObjectUtils.isEmpty(protocol)){
            jdbcUrl.append(protocol + "://");
        }
        jdbcUrl.append(urlInfoMap.get(UrlUtil.PARAM_HOST));
        String port = ObjectUtils.isEmpty(urlInfoMap.get(UrlUtil.PARAM_PORT)) ? "9200" : urlInfoMap.get(UrlUtil.PARAM_PORT);
        jdbcUrl.append(":" + port);
        return jdbcUrl.toString();
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
    public Object query(String querySql) throws ApplicationRuntimeException {
        try {
            return this.getPreparedStatement(querySql).executeQuery();
        } catch (SQLException e) {
            logger.error("Fail to query data from mysql! querySql is: {}, Exception information as follows: {}", querySql, e);
            throw new ApplicationRuntimeException(ApplicationErrorCodeEnum.DATABASE_QUERY_ERROR.getErrorCode(), ApplicationErrorCodeEnum.DATABASE_QUERY_ERROR.getErrorMessage());
        }
    }

    /**
     * ES 不支持 select 1 as cnt 语法
     * @reference-doc
     *  [1] https://github.com/opendistro-for-elasticsearch/sql/tree/main/sql-jdbc
     *  [2] Elasticsearch JDBC案例介绍 - CSDN - https://blog.csdn.net/weixin_33698043/article/details/91645394
     * @return
     */
    @Override
    public boolean health(){
        boolean health = false;
        String sql = "SHOW TABLES LIKE '%%'";
        try(Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if(!ObjectUtils.isEmpty(resultSet)){
                ResultSetMetaData metaData = resultSet.getMetaData();
                if(!ObjectUtils.isEmpty(metaData)){
                    int columnCount = metaData.getColumnCount();
                    logger.debug("[healthStatus] columnCount: {}", columnCount);
                    if(columnCount>0){
                        health = true;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("occurs a exception when detect health of elasticsearch connector/connection! datasource-info: {}", this.getDataSource());
            throw new RuntimeException(e);
        }
        return health;
    }

    @Override
    public Connection getConnection() {
        return super.getConnection();
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return this.getConnection().prepareStatement(sql);
    }

    @Override
    public void close() {
        super.close();
    }
}
