package cn.johnnyzen.common.datasource.query;

import cn.johnnyzen.common.datasource.connector.AbstractConnector;
import cn.johnnyzen.common.datasource.entity.QueryJobInfo;
import cn.johnnyzen.common.dto.page.PageResponse;
import cn.johnnyzen.common.exception.ApplicationRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.Map;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/11/14 23:22
 * @description 查询接口
 */
public abstract class AbstractQuery<T extends AbstractConnector> {
    private final static Logger logger = LoggerFactory.getLogger(AbstractQuery.class);

    protected T connector;

    /**
     * 构造器
     * @param builtConnector 已 build(即 已建立起 connection) 的 connector
     */
    public AbstractQuery(T builtConnector){
        this.connector = builtConnector;
    }

    /**
     * 普通查询
     * @param queryJobInfo
     * @param params
     * @return
     * @throws ApplicationRuntimeException
     * @throws SQLException
     */
    //public abstract PageResponse query(String sqlTemplate, Map<String, Object> dynamicPara, InfluxDbConnector connector) throws CommonException;
    public abstract PageResponse query(QueryJobInfo queryJobInfo, Map<String, Object> params) throws ApplicationRuntimeException, SQLException;

    /**
     * 自动分页查询
     * @param queryJobInfo
     * @param params
     * @return
     * @throws ApplicationRuntimeException
     * @throws SQLException
     */
    public abstract PageResponse autoPagingQuery(QueryJobInfo queryJobInfo, Map<String, Object> params) throws ApplicationRuntimeException, SQLException;

    /**
     * 获取 分页统计的 模板 SQL
     * @description
     *  实例方法，支持子类复写本方法
     * @param queryJobInfo
     * @return
     */
    public String getCountSqlTemplate(QueryJobInfo queryJobInfo){
        String countSqlTemplate = null;
        if(queryJobInfo.getSqlSupportAutoPagable()){//
            countSqlTemplate = queryJobInfo.getCountSqlTemplate();
            //countSqlTemplate 为空时: 启用默认的统计总记录的方式 ↓
            if(StringUtils.isEmpty(countSqlTemplate)){
                countSqlTemplate = "SELECT COUNT(1) AS cnt FROM (" + queryJobInfo.getSqlTemplate() + " )";
            }
        }
        return countSqlTemplate;
    }

    @Override
    public String toString() {
        return "AbstractQuery{" +
                "connector=" + connector +
                '}';
    }
}
