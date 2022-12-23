package cn.seres.bd.dataservice.model.entity;

import cn.seres.bd.dataservice.model.datasource.DataSource;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 查询类型任务实体
 */
@Data
@AllArgsConstructor
public class QueryJobInfo {

    private static final long serialVersionUID = 1L;

    private String serviceId;
    private String serviceNameEn;
    private String serviceNameCn;
    private String version;
    private String serviceDesrciption;
    private String datasourceId;
    private String serviceType;
    private String sqlTemplate;
    private String countSqlTemplate;
    /**
     * 是否 SQL支持程序自动化分页
     */
    private Boolean isSqlSupportAutoPaging;
    private String requiredParam;
    private String apiUrl;
    private String preProcessType;
    private String preProcessFunc;
    private String postProcessType;
    private String postProcessFunc;
    private String responseFormat;
    private String publishStatus;
    private String datasourceName;
    private String datasourceDesrciption;
    private String pluginPath;
    private String datasourceType1;
    private String datasourceType2;
    private String datasourceVersion;
    private String datasourceDriverClass;
    private String datasourceUrl;
    private String datasourceUser;
    private String datasourcePassword;

    /**
     * 转换为 DataSource 对象
     * @return
     */
    public DataSource toDataSource(){
        DataSource dataSource = new DataSource();
        dataSource.setDatasourceId(this.getDatasourceId());
        dataSource.setDatasourceName(this.getDatasourceName());
        dataSource.setDatasourceDriverClass(this.getDatasourceDriverClass());
        dataSource.setDatasourceUrl(this.getDatasourceUrl());
        dataSource.setDatasourceUser(this.getDatasourceUser());
        dataSource.setDatasourcePassword(this.getDatasourcePassword());
        dataSource.setDatasourceDescription(this.getDatasourceDesrciption());
        dataSource.setDatasourceType1(this.datasourceType1);
        dataSource.setDatasourceType2(this.getDatasourceType2());
        dataSource.setDatasourceVersion(this.getDatasourceVersion());
        return dataSource;
    }

    /**
     * 调度执行参数
     */
//    private Map<String, String> schedulerParam;
}
