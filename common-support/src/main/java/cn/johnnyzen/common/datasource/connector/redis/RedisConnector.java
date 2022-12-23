package cn.johnnyzen.common.datasource.connector.redis;

import cn.johnnyzen.common.datasource.connector.AbstractConnector;
import cn.johnnyzen.common.datasource.DataSource;
import cn.johnnyzen.common.exception.ApplicationRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/11/24 10:03
 * @description redis 连接工具
 *  1、url 的格式说明:
 *      format: {host}[:{port}]
 *      当 url 中不提供 port 时，默认为: 6379
 * @reference-doc
 *  [1] Java之连接Redis - CSDN - https://blog.csdn.net/jc_hook/article/details/119566864
 */
public class RedisConnector extends AbstractConnector<Jedis> {
    private static Logger logger = LoggerFactory.getLogger(RedisConnector.class);

    private static final Integer DEFAULT_PORT = 6379;

    /**
     * URL 中的分隔符
     * @description
     *  IF DEFAULT_URL_SEPARATOR_CHAR = ":"; THEN "{host}:{port}" := "{url}"
     */
    private static final String DEFAULT_URL_SEPARATOR_CHAR = ":";

    /**
     * 健康测试的目标值
     */
    private static String HEALTH_TEST_TARGET_VALUE = "PONG";

    /**
     * host, based on parse the value of `url` when call method `build()`;
     */
    private String host;

    /**
     * port, based on parse the value of `url` when call method `build()`; default value is `DEFAULT_PORT`
     */
    private Integer port = DEFAULT_PORT;

//    protected Jedis connection;

    public RedisConnector(DataSource dataSource){
        super(dataSource);
    }

    public RedisConnector(String host, Integer port, String password) {
        super(new DataSource(host + DEFAULT_URL_SEPARATOR_CHAR + port,null, password));
    }

    public RedisConnector(String url, String password) {
        super(new DataSource(url, null, password));
    }

    public RedisConnector(String host, Integer port) {
        this(host, port, null);
    }

    public RedisConnector(String url) {
        this(url, (String) null);
    }

    @Override
    protected synchronized Jedis createConnection() {
        if (this.connection == null) {
            try {
                parseUrl(this.getDataSource().getDatasourceUrl());
                String clientName = "connection-pool#jedis#connect-at-" + System.currentTimeMillis();
                String password = this.getDataSource().getDatasourcePassword();
                String host = this.host;
                Integer port = getPortOrDefaultPort(this.port);
                logger.debug("current datasource info is : {}", this.getDataSource().toString());
                logger.debug("current redis connector info # host:{}, port: {}");

                this.connection = new Jedis(host, port);
                if(!StringUtils.isEmpty(password)) {
                    this.connection.auth(password);
                }
                this.connection.clientSetname(clientName);
                logger.debug("ping result of connection-client({}) : {}", clientName, this.connection.ping());
            } catch (Exception exception) {
                exception.printStackTrace();
                logger.error("fail to get redis jdbc connection because `Exception`!db-connector-config:{}", this.toString());
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
    public Object query(String querySql) throws ApplicationRuntimeException {
        //TODO
        throw new ApplicationRuntimeException("RedisConnector # query(String querySql, Map<String, Object> params) 尚未实现");
    }

    @Override
    public void close(){
        this.connection.close();
        logger.warn("success to close current connection for redis! instanceId: {}", this.instanceId);
    }

    @Override
    public boolean health(){
        String pingResult = this.connection.ping();
        if(pingResult.equalsIgnoreCase(HEALTH_TEST_TARGET_VALUE)) {
            return true;
        }
        return false;
    }

    /**
     * 测试连接可达性
     * @param message
     *  IF message is not empty, THEN RETURN value = message
     * @return
     *  IF message is not empty,
     *      THEN call `this.connection.ping({message});` IF health RETURN value = {message}.
     *  IF message is empty, THEN call `this.connection.ping();`
     *      IF health RETURN value = "PONG"
     */
    public String ping(String message){
        if(StringUtils.isEmpty(message)){
            return this.connection.ping();
        } else {
            return this.connection.ping(message);
        }
    }

    /**
     * get `host` and `port` by parse `url`
     */
    protected void parseUrl(String url){
        if(StringUtils.isEmpty(url)){
            logger.error("url is empty!");
        }
        String [] hostAndPort = url.split(DEFAULT_URL_SEPARATOR_CHAR);
        this.host = hostAndPort[0];
        if(hostAndPort.length<2){
            logger.warn("`port` is empty, then this variable will be set a default value(`{}`)!", DEFAULT_PORT);
            this.port = getPortOrDefaultPort(null);
        } else {
            this.port = getPortOrDefaultPort(Integer.valueOf(hostAndPort[1]));
        }
        logger.debug("current url({}) has be parsed!the host is {}, and the port is {}", url, host, port);
    }

    public static Integer getPortOrDefaultPort(Integer port){
        return port==null?DEFAULT_PORT:port;
    }
}
