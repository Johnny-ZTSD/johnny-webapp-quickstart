package cn.johnnyzen.common.datasource.connector;

import cn.johnnyzen.common.datasource.DataSource;
import cn.johnnyzen.common.exception.ApplicationRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/12/1 16:57
 * @description 假定 XXConnector extends AbstractConnector<T=InfluxDB/java.sql.Connection>
 *  1、1个本抽象类的实现类实例对象，与 T:connection 实例对象一一对应，二者属于包装关系
 *      1） 1个 Connector 实例对象 即 一个产生一个 connection 连接实例
 *      2） 1个 xxConnector 类，含 多个 connector 实例对象
 *  2、获取本抽象类的实现类实例对象 的2种方式：
 *      AbstractConnector<T=InfluxDB> connector = new XXConnector();
 *      AbstractConnector<T=InfluxDB> connector = new XXConnector(DataSource:datasource);
 *  3、通过 本抽象类的实现类实例对象 获取 connection 的2种方式:
 *      方式1:
 *          AbstractConnector<T=InfluxDB> connector = new XXConnector();
 *          connector.config(DataSource:datasource).build();
 *          T connection = connector.getConnection();
 *      方式2:
 *          AbstractConnector<T=InfluxDB> connector = new XXConnector(DataSource:datasource);
 *          T connection = connector.getConnection();
 *  4、通过 connection 反向实例化 本抽象类的实现类实例对象 的唯一方式: [X] 暂不支持此方式(因: url/username/password 无法通过 connection 获取到)
 *      AbstractConnector<T> connector = AbstractConnector.getConnector(T:connection)
 * @reference-doc
 *  [1] 双重检查锁定（Double-Checked Locking）的问题和解决方案 - CSDN - https://blog.csdn.net/u013490280/article/details/108722926
 */
public abstract class AbstractConnector<T> {
    private static Logger logger = LoggerFactory.getLogger(AbstractConnector.class);

    /**
     * 本类的实例对象合集
     * @description
     *  key = Connection(:T)
     *  value = instanceId
     */
    protected static Map<Object, Integer> instances = new HashMap<>();

    /**
     * 实例对象 ID
     * 每次实例化对象时借助 instances 获取自增编号
     */
    protected Integer instanceId;

    /**
     * 是否构建过连接
     * 构建: 即 初始化 connection
     */
    protected Boolean isBuildedConnection = false;

    /**
     * 数据库连接配置信息
     */
    protected DataSource dataSourceConfig;

    /**
     * 包装的数据库连接对象 与 本 connector 实例对象一一对应
     * @description T 一般为:
     *  java.sql.Connection
     *  org.influxdb.InfluxDB
     *  redis.clients.jedis.Jedis
     *  ...
     */
    protected T connection;

    public AbstractConnector(){
        this.isBuildedConnection = false;
    }

    public AbstractConnector(DataSource dataSourceConfig) {
        config(dataSourceConfig);
        build();
    }

    public AbstractConnector<T> config(DataSource dataSourceConfig){
        this.dataSourceConfig = dataSourceConfig;
        return this;
    }

    public AbstractConnector<T> build(){
        /* 防止反复 build */
        if(isBuildedConnection == true){
            return this;
        }
        if(ObjectUtils.isEmpty(dataSourceConfig)){
            throw new ApplicationRuntimeException("datasource information must be not empty!");
        }
        this.instanceId = instances.size()+1;
        this.connection = this.createConnection();
        instances.put(this, this.instanceId);
        this.isBuildedConnection = true;
        return this;
    }

    /**
     * connection 实例对象 初始化
     * @desciption 每种数据库的实现方式均不同
     * @isMustImplementsThisAbstractMethod true(必须实现本抽象方法)
     * @return
     * @param <T>
     */
    protected abstract <T> T createConnection();

    /**
     * 注册驱动器类
     * @description
     *  部分数据源初次建立数据库连接时需加载驱动类，例如: MySQL 等
     * @isMustImplementsThisAbstractMethod false(非必须实现本抽象方法)
     */
    protected abstract void registerDriver();

    /**
     * 使用当前 connection 对象 查询 数据库数据
     * @isMustImplementsThisAbstractMethod false(非必须实现本抽象方法,但建议实现)
     * @param querySql
     * @param params 自定义的各种附加属性 key=自定义属性的名称, value=属性值
     * @return
     * @throws ApplicationRuntimeException
     */
    public abstract Object query(String querySql, Map<String, Object> params) throws ApplicationRuntimeException;
    public abstract Object query(String querySql) throws ApplicationRuntimeException;

    /**
     * 当前 connection 是否健康
     * @isMustImplementsThisAbstractMethod true(必须实现本抽象方法,但建议实现)
     * @return
     *  true  : 连接健康
     *  false : 连接异常
     */
    public boolean health(){
        boolean health = true;
        if(ObjectUtils.isEmpty( this.connection )) {
            health = false;
            return health;
        }
        if(this.connection instanceof Connection){
            try {
                ResultSet resultSet = (ResultSet) this.query("select 1 as cnt");
                while (resultSet.next()) {
                    int result = resultSet.getInt("cnt");
                    if(result!=1){
                        health = false;
                    }
                }
            } catch (SQLException e) {
                logger.error("Fail to detect health status of connector and throw a `SQLException`! instanceId: {}, exception: {}", instanceId, e);
                health = false;
            } catch (ApplicationRuntimeException e) {
                logger.error("Fail to detect health status of connector and throw a `CommonException`! instanceId: {}, exception: {}", instanceId, e);
                health = false;
            }
        } else {
            throw new ApplicationRuntimeException("unkown type for `this.connection`!");
        }
        return health;
    }

    /**
     * 关闭/释放当前 connection 实例
     * @isMustImplementsThisAbstractMethod true(必须实现本抽象方法,但建议实现)
     */
    public void close(){
        if(!ObjectUtils.isEmpty( this.connection )) {
            if(this.connection instanceof Connection){
                Connection con = (Connection) this.connection;
                try {
                    con.close();
                } catch (SQLException e) {
                    logger.error("Fail to close/release this connection and throw a `SQLException`: {}, currentInstanceId: {}", e, instanceId);
                }
                con = null;
                this.connection = null;
            } else {
                throw new ApplicationRuntimeException("unkown type for `this.connection`!");
            }
        }
        logger.info("success to close/release this connection. currentInstanceId: {}", instanceId);
    }

    /**
     * 获取当前连接实例对象的实例ID
     * @return
     */
    public Integer getInstanceId() {
        return instanceId;
    }

    /**
     * 获取由当前类产生的连接实例对象的总个数
     * @return
     */
    public static Integer getInstancesSize(){
        return instances.entrySet().size();
    }

    public DataSource getDataSource() {
        return dataSourceConfig;
    }

    /**
     * 获取 connection 连接
     * @description 先 build , 后 get Connection
     * @return
     */
    public T getConnection() {
        if(isBuildedConnection == false){
            String message = "The connection has not been built/initialized for the current connector object! instanceId=" + instanceId;
            logger.debug(message);
            throw new ApplicationRuntimeException(message);
        }
        return connection;
    }

    public static Set<Object> getAllConnectors(){
        return instances.keySet();
    }

    public static Collection<Integer> getAllInstanceIds(){
        return instances.values();
    }

    @Override
    public String toString() {
        return "AbstractConnector{" +
                "instanceId=" + instanceId +
                ", dataSource='" + dataSourceConfig.toString() + '\'' +
                ", connection=" + connection +
                '}';
    }
}
