package cn.johnnyzen.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 * <p>Title: LoggerUtil.java</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c)2016</p>
 * <p>Company: 四方伟业</p>
 * <p>CreateTime: 2016年11月30日 上午10:10:20</p>
 *
 * @author zhouyh
 * @version 1.0
 */
public class LoggerUtil {

    //日志定义-------------start-------------------

    /**
     * 连接池
     */
    private static final String DB_POOL_MNG_LOGGER = "DB_POOL_MNG";

    /**
     * restful日志定义
     */
    private static final String API_MNG_LOGGER = "API_MNG";

    /**
     * service日志定义
     */
    private static final String CORE_MNG_LOGGER = "CORE_MNG";


    /**
     * 日志定义
     */
    private static final String DATABASE_MNG_CORE_LOGGER = "DATABASE_MNG_CORE";

    /**
     * 元模型日志定义
     */
    private static final String METAMODEL_MNG_FACADE_LOGGER = "METAMODEL_MNG_FACADE";

    /**
     * 元模型日志定义
     */
    private static final String METAMODEL_MNG_CORE_LOGGER = "METAMODEL_MNG_CORE";

    /**
     * 数据服务日志定义
     */
    private static final String DATASERVICE_MNG_CORE_LOGGER = "DATASERVICE_MNG_CORE";

    /**
     * 建模日志定义
     */
    private static final String MODEL_MNG_CORE = "MODEL_MNG_CORE";

    /**
     * 元数据采集任务日志定义
     */
    private static final String METADATACOLL_MNG_FACADE_LOGGER = "METADATACOLL_MNG_FACADE";

    /**
     * 元数据日志定义
     */
    //add by zhoufaxuan
    private static final String METADATA_MNG_FACADE_LOGGER = "METADATA_MNG_FACADE";

    /**
     * 执行器日志定义
     */
    private static final String EXECUTOR_MNG_CORE_LOGGER = "EXECUTOR_MNG_CORE_LOGGER";

    /**
     * 模型设计
     */
    private static final String MODEL_DESIGN_MNG_CODE_LOGGER = "MODEL_DESIGN_MNG_CODE_LOGGER";

    /**
     * 数据标准日志定义
     */
    private static final String DATA_STANDARD_MNG_CORE_LOGGER = "DATA_STANDARD_MNG_CORE";
    private static final String SYS_VAR_CORE_LOGGER = "SYS_VAR_CORE";
    private static final String NAME_RULE_CORE_LOGGER = "NAME_RULE_CORE";
    private static final String BUSINESS_CODE_RULE_CORE_LOGGER = "NAME_RULE_CORE";
    private static final String DATA_DATAMAP_MNG_CORE_LOGGER = "DATA_DATAMAP_CORE_LOGGER";
    private static final String METADATA_AUTHORITY_CORE_LOGGER = "METADATA_AUTHORITY_CORE_LOGGER";

    private static final String METADATACOLL_EXECUTOR_MNG_CORE_LOGGER = "METADATA_EXECUTOR_MNG_CORE_LOGGER";

    private static final String ASSET_LIFE_CYCLE_MNG_CORE_LOGGER = "ASSET_LIFE_CYCLE_MNG_CORE_LOGGER";

    private static final String ASSET_MAIN_DATA_MNG_CORE_LOGGER = "ASSET_MAIN_DATA_MNG_CORE_LOGGER";

    private static final String ASSET_SKYDRIVE_MNG_CORE_LOGGER = "ASSET_SKYDRIVE_MNG_CORE_LOGGER";

    /**
     * 元数据用户订阅日志定义
     */
    private static final String USER_FAVORITE_MNG_CORE_LOGGER = "USER_FAVORITE_MNG_GORE_LOGGER";
    private static final String METADATA_AUDIT_MNG_CORE_LOGGER = "METADATA_AUDIT_MNG_CORE_LOGGER";
    /**
     * 事件容器日志
     */
    private static final String EVENT_CONTAINER_MNG_CORE_LOGGER = "EVENT_CONTAINER_MNG_CORE_LOGGER";

    /**
     * 审核日志定义
     */
    private static final String AUDIT_CORE_LOGGER = "AUDIT_CORE_LOGGER";
    /**
     * 数据稽查日志定义
     */
    private static final String DATA_ANALYZE_LOGGER = "DATA_ANALYZE_LOGGER";

    /**
     * 主数据日志定义
     */
    private static final String MASTER_DATA_LOGGER = "MASTER_DATA_LOGGER";

    /*
     * 数据模型日志定义
     */
    private static final String DATAMODEL_MNG_CORE_LOGGER = "DATAMODEL_MNG_CORE";

    /**
     * 资源目录日志名称
     */
    private static final String RESOURCE_CATALOG_LOGGER = "RESOURCE_CATALOG";


    /**
     * 异步任务日志定义
     */
    private static final String TASK_MNG_LOG_MNG = "TASK_MNG_LOG_MNG";

    /**
     * warn 告警模块 日志定义
     */
    private static final String WARNINNG_LOGGER = "WARNINNG_LOGGER";


    //日志定义 -------------end-------------------

    //日志实例-------------start-------------------

    /**
     * 模型实例
     */
    public static Logger MODEL_DESIGN_MNG_CODE_LOGGER_INSTANCE = LoggerFactory.getLogger(MODEL_DESIGN_MNG_CODE_LOGGER);

    /**
     * 日志实例
     */
    public static Logger DB_POOL_MNG_LOGGER_INSTANCE = LoggerFactory.getLogger(DB_POOL_MNG_LOGGER);

    /**
     * 日志实例
     */
    public static Logger API_MNG_LOGGER_INSTANCE = LoggerFactory.getLogger(API_MNG_LOGGER);

    /**
     * 日志实例
     */
    public static Logger CORE_MNG_LOGGER_INSTANCE = LoggerFactory.getLogger(CORE_MNG_LOGGER);

    /**
     * 建模日志实例
     */
    public static Logger MODEL_MNG_CORE_LOGGER_INSTANCE = LoggerFactory.getLogger(MODEL_MNG_CORE);


    /**
     * 日志实例
     */
    public static Logger DATABASE_MNG_CORE_LOGGER_INSTANCE = LoggerFactory
            .getLogger(DATABASE_MNG_CORE_LOGGER);

    /**
     * 元模型日志实例
     */
    public static Logger METAMODEL_MNG_FACADE_LOGGER_INSTANCE = LoggerFactory
            .getLogger(METAMODEL_MNG_FACADE_LOGGER);

    /**
     * 元模型日志实例
     */
    public static Logger METAMODEL_MNG_CORE_LOGGER_INSTANCE = LoggerFactory
            .getLogger(METAMODEL_MNG_CORE_LOGGER);

    /**
     * 数据服务日志实例
     */
    public static Logger DATASERVICE_MNG_CORE_LOGGER_INSTANCE = LoggerFactory
            .getLogger(DATASERVICE_MNG_CORE_LOGGER);
    /**
     * 元数据采集任务日志实例
     */
    public static Logger METADATACOLL_MNG_FACADE_LOGGER_INSTANCE = LoggerFactory
            .getLogger(METADATACOLL_MNG_FACADE_LOGGER);
    //add by zhoufaxuan
    /**
     * 元数据日志实例
     */
    public static Logger METADATA_MNG_FACADE_LOGGER_INSTANCE = LoggerFactory
            .getLogger(METADATA_MNG_FACADE_LOGGER);
    /**
     * 执行器务日志实例
     */
    public static Logger EXECUTOR_MNG_CORE_LOGGER_INSTANCE = LoggerFactory
            .getLogger(EXECUTOR_MNG_CORE_LOGGER);

    /**
     * 数据标准日志实例
     */
    public static Logger DATA_STANDARD_INSTANCE = LoggerFactory
            .getLogger(DATA_STANDARD_MNG_CORE_LOGGER);

    /**
     * 系统变量日志实例
     */
    public static Logger SYS_VAR_INSTANCE = LoggerFactory
            .getLogger(SYS_VAR_CORE_LOGGER);
    /**
     * 命名规则日志实例
     */
    public static Logger NAME_RULE_INSTANCE = LoggerFactory
            .getLogger(NAME_RULE_CORE_LOGGER);

    /**
     * 命名规则日志实例
     */
    public static Logger BUSINESS_CODE_RULE_INSTANCE = LoggerFactory
            .getLogger(BUSINESS_CODE_RULE_CORE_LOGGER);

    /**
     * 数据地图日志实例
     */
    public static Logger DATA_DATAMAP_CORE_LOGGER = LoggerFactory
            .getLogger(DATA_DATAMAP_MNG_CORE_LOGGER);
    public static Logger METADATA_AUTHORITY_LOGGER_INSTANCE = LoggerFactory
            .getLogger(METADATA_AUTHORITY_CORE_LOGGER);
    /**
     * 元数据采集执行器日志实例
     */
    public static Logger METADATACOLL_EXECUTOR_MNG_CORE_LOGGER_INSTANCE = LoggerFactory
            .getLogger(METADATACOLL_EXECUTOR_MNG_CORE_LOGGER);
    /**
     * 生命周期管理
     */
    public static Logger ASSET_LIFE_CYCLE_MNG_CORE_LOGGER_INSTANCE = LoggerFactory.
            getLogger(ASSET_LIFE_CYCLE_MNG_CORE_LOGGER);

    /**
     * 主数据
     */
    public static Logger ASSET_MAIN_DATA_MNG_CORE_LOGGER_INSTANCE = LoggerFactory.
            getLogger(ASSET_MAIN_DATA_MNG_CORE_LOGGER);

    /**
     * 网盘
     */
    public static Logger ASSET_SKYDRIVE_MNG_CORE_LOGGER_INSTANCE = LoggerFactory.getLogger(ASSET_SKYDRIVE_MNG_CORE_LOGGER);


    /**
     * 用户元数据订阅
     */
    public static Logger USER_FAVORITE_MNG_CORE_LOGGER_INSTANCE = LoggerFactory.
            getLogger(USER_FAVORITE_MNG_CORE_LOGGER);

    /**
     * 元数据稽核
     */
    public static Logger METADATA_AUDIT_MNG_CORE_LOGGER_INSTANCE = LoggerFactory
            .getLogger(METADATA_AUDIT_MNG_CORE_LOGGER);

    /**
     * 审核日志示例
     */
    public static Logger AUDIT_CORE_LOGGER_INSTANCE = LoggerFactory.getLogger(AUDIT_CORE_LOGGER);
    /**
     * 数据稽查
     */
    public static Logger DATA_ANALYZE_LOGGER_INSTANCE = LoggerFactory.getLogger(DATA_ANALYZE_LOGGER);
    /**
     * 主数据模块
     */
    public static Logger MASTER_DATA_LOGGER_INATANCE = LoggerFactory.getLogger(MASTER_DATA_LOGGER);

    /**
     * 数据模型模块
     */

    public static Logger DATAMODEL_MNG_CORE_LOGGER_INSTANCE = LoggerFactory.getLogger(DATAMODEL_MNG_CORE_LOGGER);

    /**
     * 资源目录日志实例
     */
    public static Logger RESOURCE_CATALOG_LOGGER_INSTANCE = LoggerFactory.getLogger(RESOURCE_CATALOG_LOGGER);

    /**
     * 事件容器
     */
    public static Logger EVENT_CONTAINER_MNG_CORE_LOGGER_INSTANCE = LoggerFactory
            .getLogger(EVENT_CONTAINER_MNG_CORE_LOGGER);



    /**
     * 异步任务日志定义
     */
    public static Logger TASK_MNG_LOGGER_INSTANCE = LoggerFactory
            .getLogger(TASK_MNG_LOG_MNG);

    /**
     * 模型实例
     */
    public static Logger WARNING_LOGGER_INSTANCE = LoggerFactory.getLogger(WARNINNG_LOGGER);

    //日志实例-------------end-------------------

    //日志定义 end

    /**
     *
     */
    private static final char THREAD_RIGHT_TAG = ']';

    /**
     *
     */
    private static final char THREAD_LEFT_TAG = '[';

    /**
     *
     */
    public static final char ENTERSTR = '\n';

    /**
     *
     */
    public static final char COMMA = ',';

    /**
     *
     */
    public static final char LINE = '|';

    /**
     * create LoggerUtil instance
     */
    private LoggerUtil() {

    }

    /**
     * @param logger
     * @param obj
     * @author zhouyh
     * @createtime 2017年1月12日 下午2:55:14
     */
    public static void debug(Logger logger, Object... obj) {
        if (logger.isDebugEnabled()) {
            logger.debug(getLogString(obj));
        }
    }

    /**
     * @param logger
     * @param obj
     * @author zhouyh
     * @createtime 2017年1月12日 下午2:55:20
     */
    public static void info(Logger logger, Object... obj) {
        if (logger.isInfoEnabled()) {
            logger.info(getLogString(obj));
        }
    }

    /**
     * @param logger
     * @param obj
     * @author zhouyh
     * @createtime 2017年1月12日 下午2:55:26
     */
    public static void warn(Logger logger, Object... obj) {
        if (logger.isWarnEnabled()) {
            logger.warn(getLogString(obj));
        }
    }

    /**
     * @param logger
     * @param obj
     * @author zhouyh
     * @createtime 2017年1月12日 下午2:55:31
     */
    public static void error(Logger logger, Object... obj) {
        logger.error(getLogString(obj));
    }

    /**
     * @param logger
     * @param e
     * @param obj
     * @author zhouyh
     * @createtime 2017年1月12日 下午2:55:37
     */
    public static void error(Logger logger, Throwable e, Object... obj) {
        logger.error(getLogString(obj), e);
    }

    /**
     * 组装日志信息
     *
     * @param obj
     * @return
     * @author zhouyh
     * @createtime 2016年11月30日 上午10:11:51
     */
    private static String getLogString(Object... obj) {
        StringBuilder log = new StringBuilder();
        log.append(THREAD_LEFT_TAG).append(Thread.currentThread().getId()).append(THREAD_RIGHT_TAG);

        for (Object o : obj) {
            log.append(o);

        }
        return log.toString();
    }

}
