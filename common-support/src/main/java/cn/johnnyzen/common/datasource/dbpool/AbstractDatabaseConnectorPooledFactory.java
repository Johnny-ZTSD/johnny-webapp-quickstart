package cn.johnnyzen.common.datasource.dbpool;

//import org.apache.commons.pool2.PooledObjectFactory;
import cn.johnnyzen.common.datasource.entity.DataSource;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/12/2 0:37
 * @description 数据库连接池工厂类
 *  1、基于 org.apache.commons.pool2.BasePooledObjectFactory or PooledObjectFactory
 *  2、总体流程 //TODO
 *      在服务启动时，
 *          依次加载各项目的各数据源的 连接池池化工厂(每种数据库实例化1个 xxPooledObjectFactory bean)， 存放到 Map<String, xxPooledObjectFactory> databaseConnectorManager : key="poolName"="datasourceId#datasourceName", value = xxPooledObjectFactory 实例对象
 *              每个 key 一一对应 1 条 datasource 配置记录
 *              allDatasources =  datasourceMapper.findAll()
 *              for datasource in allDatasources:
 *                  key = poolName = "datasourceId#datasourceName";
 *                  switch(datasource.dbType){
 *                      case(INFLUXDB):
 *                          Connector connector = influxPooledObjectFactory.put(poolName, datasource);
 *                  }
 *                  databaseConnectorManager.put(key=poolName="datasourceId#datasourceName", value = xxPooledObjectFactory object);
 *      在查询应用数据接口时
 *          datasource = datasourceMapper.getDatasourceById(datasourceId);
 *          key = poolName = "datasourceId#datasourceName";
 *          switch(datasource.dbType):{
 *              case(INFLUXDB):
 *                  Connector connector = influxPooledObjectFactory.get(poolName);
 *          }
 *          xxPooledObjectFactory databaseConnectorManager.get(poolName);
 *  3、
 *      public abstract class AbstractDatabaseConnectorPooledFactory<T> extends PooledObjectFactory<T>
 *      public abstract class AbstractDatabaseConnectorPooledFactory<T> extends BasePooledObjectFactory<T>
 *
 *      public abstract class AbstractDatabaseConnectorPooledFactory<K, V> extends KeyedPooledObjectFactory<K, V>
 *          k := poolName
 *          v = xxDatabaseConnectorPool
 *      public abstract class AbstractDatabaseConnectorPooledFactory<K, V> extends BaseKeyedPooledObjectFactory<K, V=IPooled>
 */

public abstract class AbstractDatabaseConnectorPooledFactory<T> extends BasePooledObjectFactory<T> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDatabaseConnectorPooledFactory.class);
    /**
     * 数据库连接配置信息
     */
    protected DataSource dataSource;

    /**
     * 实例化对象的唯一方式
     * @param dataSource
     */
    public AbstractDatabaseConnectorPooledFactory(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public String toString() {
        return "AbstractDatabaseConnectorPooledFactory{" +
                "dataSource=" + dataSource +
                '}';
    }
}
