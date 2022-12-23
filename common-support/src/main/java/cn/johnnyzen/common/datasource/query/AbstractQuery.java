package cn.seres.bd.dataservice.common.query;

import cn.seres.bd.dataservice.common.connector.AbstractConnector;
import cn.seres.bd.dataservice.common.dto.page.PageResponse;
import cn.seres.bd.dataservice.common.exception.CommonException;
import cn.seres.bd.dataservice.model.datasource.DataSource;
import cn.seres.bd.dataservice.model.entity.QueryJobInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.Map;

/**
 * @author 408675 (tai.zeng@seres.cn)
 * @version v1.0
 * @project bdp_common_data_service
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
     * @throws CommonException
     * @throws SQLException
     */
    //public abstract PageResponse query(String sqlTemplate, Map<String, Object> dynamicPara, InfluxDbConnector connector) throws CommonException;
    public abstract PageResponse query(QueryJobInfo queryJobInfo, Map<String, Object> params) throws CommonException, SQLException;

    /**
     * 自动分页查询
     * @param queryJobInfo
     * @param params
     * @return
     * @throws CommonException
     * @throws SQLException
     */
    public abstract PageResponse autoPagingQuery(QueryJobInfo queryJobInfo, Map<String, Object> params) throws CommonException, SQLException;

    /**
     * 获取 分页统计的 模板 SQL
     * @description
     *  实例方法，支持子类复写本方法
     * @param queryJobInfo
     * @return
     */
    public String getCountSqlTemplate(QueryJobInfo queryJobInfo){
        String countSqlTemplate = null;
        if(queryJobInfo.getIsSqlSupportAutoPaging()){//
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
