package cn.johnnyzen.common.datasource.dbpool.redis;

import cn.johnnyzen.common.datasource.entity.DataSource;
import cn.johnnyzen.common.datasource.connector.redis.RedisConnector;
import cn.johnnyzen.common.datasource.dbpool.AbstractDatabaseConnectorPooledFactory;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 408675 (tai.zeng@seres.cn) / Jenson
 * @version v1.0
 * @project bdp_common_data_service
 * @create-time 2022/12/1 13:54
 * @description 数据库池化工厂
 * @reference-doc
 *  [1] 基于Commons-Pool2实现自己的redis连接池 - 京东云 - https://developer.jdcloud.com/article/2097 【推荐】
 *  [2] java 对象池 commons-pool2 的使用(创建influxDB连接池) - jianshu - https://www.jianshu.com/p/46c27ee9e110
 */
public class RedisConnectorPooledFactory extends AbstractDatabaseConnectorPooledFactory<RedisConnector> {
    private static final Logger logger = LoggerFactory.getLogger(RedisConnectorPooledFactory.class);

    public RedisConnectorPooledFactory(DataSource dataSource){
        super(dataSource);
    }

    /**
     * 拿取时调用 - 可不重写
     * @description 重新初始化要由池返回的实例-即从池中借用一个对象时调用
     * @param pooledObject 一个 PooledObject 包装要激活的实例
     * @throws Exception
     */
    @Override
    public void activateObject(PooledObject<RedisConnector> pooledObject) throws Exception {
        logger.debug("activate object for redis ...");
        super.activateObject(pooledObject);
    }

    /**
     * 破坏对象 - 可不重写
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void destroyObject(PooledObject<RedisConnector> pooledObject) throws Exception {
        logger.debug("destroying object for redis ...");
        super.destroyObject(pooledObject);
    }

    /**
     * 创造对象 - 必须重写
     * @return
     * @throws Exception
     */
    @Override
    public RedisConnector create() throws Exception {
        RedisConnector connector = new RedisConnector(dataSource);
        connector.build();
        return connector;
    }

    @Override
    public PooledObject<RedisConnector> wrap(RedisConnector connector) {
        return new DefaultPooledObject<RedisConnector>(connector);
    }

    /**
     * 创建对象
     * @description
     *  1、同 create 等效 - 因已重写了 create 方法了，此处可不再重写
     *  2、创建可由池提供服务的实例，并将其包装在由池管理的PooledObject中
     * @return
     * @throws Exception
     */
    @Override
    public PooledObject<RedisConnector> makeObject() throws Exception {
        RedisConnector connector = new RedisConnector(dataSource);
        connector.build();
        return new DefaultPooledObject<>(connector);
    }

    /**
     * 返还池子里时调用 - 可不重写
     * @description 取消初始化要返回到空闲对象池的实例-即从池中归还一个对象时调用
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void passivateObject(PooledObject<RedisConnector> pooledObject) throws Exception {
        logger.debug("passivating object for redis ...");
        super.passivateObject(pooledObject);
    }

    /**
     * 校验 - 可不重写
     * @description 确保实例可以安全地由池返回。
     * @param pooledObject
     * @return 如果obj无效并且应该从池中删除，则为false ，否则为true
     */
    @Override
    public boolean validateObject(PooledObject<RedisConnector> pooledObject) {
        logger.debug("validating object for redis ...");
//        return super.validateObject(pooledObject);
        return pooledObject.getObject().health();
    }
}
