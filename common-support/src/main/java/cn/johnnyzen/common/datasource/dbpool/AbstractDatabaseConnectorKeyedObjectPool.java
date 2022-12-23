package cn.johnnyzen.common.datasource.dbpool;

import cn.johnnyzen.common.datasource.entity.DataSource;
import cn.johnnyzen.common.datasource.connector.AbstractConnector;
import cn.johnnyzen.common.exception.ApplicationRuntimeException;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/12/8 17:01
 * @description 抽象的数据库对象连接池
 */
public abstract class AbstractDatabaseConnectorKeyedObjectPool<K extends DataSource, V extends AbstractConnector> extends GenericKeyedObjectPool<DataSource, AbstractConnector> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDatabaseConnectorKeyedObjectPool.class);

    public AbstractDatabaseConnectorKeyedObjectPool(AbstractDatabaseConnectorKeyedPooledFactory<DataSource, AbstractConnector> factory) {
        super(factory);
    }

    public AbstractDatabaseConnectorKeyedObjectPool(KeyedPooledObjectFactory<DataSource, AbstractConnector> factory) {
        super(factory);
    }

    public AbstractDatabaseConnectorKeyedObjectPool(KeyedPooledObjectFactory<DataSource, AbstractConnector> factory, GenericKeyedObjectPoolConfig<AbstractConnector> config) {
        super(factory, config);
    }

    @Override
    public AbstractConnector borrowObject(DataSource dataSource, long borrowMaxWaitMillis) {
        if(ObjectUtils.isEmpty(dataSource)){
            throw new ApplicationRuntimeException("dataSource is empty!");
        }
        String databaseType = dataSource.getDatasourceType();
        AbstractConnector connector = null;
        boolean borrowSuccessful = true; // 默认(true): 借阅成功
        Exception exception = null;
        try{
            connector = super.borrowObject(dataSource, borrowMaxWaitMillis);
        } catch (Exception e) {
            borrowSuccessful = false;
            exception = e;
        }

        //发生异常or借阅失败时，打印当前连接池的状态信息
        if(borrowSuccessful==false){
            this.printPoolCurrentStatus(dataSource);
            String errorMessage = String.format("Fail to borrow a connector instance, databaseType: %s, datasource: %s.", databaseType, dataSource.toString());
            logger.error(errorMessage);
            logger.error("exception: {}", exception);
            throw new ApplicationRuntimeException(errorMessage);
        }
        return connector;
    }

    /**
     * 打印连接池当前的状态
     * @param dataSource 本参数可为空
     */
    public void printPoolCurrentStatus(DataSource dataSource){
        StringBuilder content = new StringBuilder();
        content.append("[poolStatus]");

        Long createdCount = this.getCreatedCount();//总创建次数
        content.append(", createdCount: {}");
        Long borrowCount = this.getBorrowedCount();//总借阅次数
        content.append(", borrowCount: {}");
        Long returnedCount = this.getReturnedCount();//总归还次数
        content.append(", returnedCount: {}");
        Long destroyedCount = this.getDestroyedCount();//总销毁次数
        content.append(", destroyedCount: {}");

        int totalNumActive = this.getNumActive();// 总活跃数
        content.append(", totalNumActive: {}");
        int totalNumIdle = this.getNumIdle(); // 总闲余数
        content.append(", totalNumIdle: {}");

        logger.debug(content.toString(), createdCount, borrowCount, returnedCount, destroyedCount, totalNumActive, totalNumIdle);

        if(!ObjectUtils.isEmpty(dataSource)){
            StringBuilder currentDatsourcePoolObjectContent = new StringBuilder();
            currentDatsourcePoolObjectContent.append("[poolStatus-for-datasource] datasourceId : {}, numActive: {}, numIdle: {}");

            int numActive = this.getNumActive(dataSource);// 本数据源连接池对象的活跃数
            currentDatsourcePoolObjectContent.append(", numActive: {}");
            int numIdle = this.getNumIdle(dataSource); // 本数据源连接池对象的闲余数
            currentDatsourcePoolObjectContent.append(", numIdle: {}");

            logger.debug(currentDatsourcePoolObjectContent.toString(), dataSource.getDatasourceId(), numActive, numIdle);
        }
    }
}
