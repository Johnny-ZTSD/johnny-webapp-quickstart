package cn.johnnyzen.common.datasource.entity;

/**
 * 查询类型任务实体
 */
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
    private Boolean sqlSupportAutoPagable;

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
    private String datasourceCategory;
    private String datasourceType;
    private String datasourceVersion;
    private String datasourceDriverClass;
    private String datasourceUrl;
    private String datasourceUser;
    private String datasourcePassword;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceNameEn() {
        return serviceNameEn;
    }

    public void setServiceNameEn(String serviceNameEn) {
        this.serviceNameEn = serviceNameEn;
    }

    public String getServiceNameCn() {
        return serviceNameCn;
    }

    public void setServiceNameCn(String serviceNameCn) {
        this.serviceNameCn = serviceNameCn;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getServiceDesrciption() {
        return serviceDesrciption;
    }

    public void setServiceDesrciption(String serviceDesrciption) {
        this.serviceDesrciption = serviceDesrciption;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getSqlTemplate() {
        return sqlTemplate;
    }

    public void setSqlTemplate(String sqlTemplate) {
        this.sqlTemplate = sqlTemplate;
    }

    public String getCountSqlTemplate() {
        return countSqlTemplate;
    }

    public void setCountSqlTemplate(String countSqlTemplate) {
        this.countSqlTemplate = countSqlTemplate;
    }

    public Boolean getSqlSupportAutoPagable() {
        return sqlSupportAutoPagable;
    }

    public void setSqlSupportAutoPagable(Boolean sqlSupportAutoPagable) {
        this.sqlSupportAutoPagable = sqlSupportAutoPagable;
    }

    public String getRequiredParam() {
        return requiredParam;
    }

    public void setRequiredParam(String requiredParam) {
        this.requiredParam = requiredParam;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getPreProcessType() {
        return preProcessType;
    }

    public void setPreProcessType(String preProcessType) {
        this.preProcessType = preProcessType;
    }

    public String getPreProcessFunc() {
        return preProcessFunc;
    }

    public void setPreProcessFunc(String preProcessFunc) {
        this.preProcessFunc = preProcessFunc;
    }

    public String getPostProcessType() {
        return postProcessType;
    }

    public void setPostProcessType(String postProcessType) {
        this.postProcessType = postProcessType;
    }

    public String getPostProcessFunc() {
        return postProcessFunc;
    }

    public void setPostProcessFunc(String postProcessFunc) {
        this.postProcessFunc = postProcessFunc;
    }

    public String getResponseFormat() {
        return responseFormat;
    }

    public void setResponseFormat(String responseFormat) {
        this.responseFormat = responseFormat;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getDatasourceName() {
        return datasourceName;
    }

    public void setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName;
    }

    public String getDatasourceDesrciption() {
        return datasourceDesrciption;
    }

    public void setDatasourceDesrciption(String datasourceDesrciption) {
        this.datasourceDesrciption = datasourceDesrciption;
    }

    public String getPluginPath() {
        return pluginPath;
    }

    public void setPluginPath(String pluginPath) {
        this.pluginPath = pluginPath;
    }

    public String getDatasourceCategory() {
        return datasourceCategory;
    }

    public void setDatasourceCategory(String datasourceCategory) {
        this.datasourceCategory = datasourceCategory;
    }

    public String getDatasourceType() {
        return datasourceType;
    }

    public void setDatasourceType(String datasourceType) {
        this.datasourceType = datasourceType;
    }

    public String getDatasourceVersion() {
        return datasourceVersion;
    }

    public void setDatasourceVersion(String datasourceVersion) {
        this.datasourceVersion = datasourceVersion;
    }

    public String getDatasourceDriverClass() {
        return datasourceDriverClass;
    }

    public void setDatasourceDriverClass(String datasourceDriverClass) {
        this.datasourceDriverClass = datasourceDriverClass;
    }

    public String getDatasourceUrl() {
        return datasourceUrl;
    }

    public void setDatasourceUrl(String datasourceUrl) {
        this.datasourceUrl = datasourceUrl;
    }

    public String getDatasourceUser() {
        return datasourceUser;
    }

    public void setDatasourceUser(String datasourceUser) {
        this.datasourceUser = datasourceUser;
    }

    public String getDatasourcePassword() {
        return datasourcePassword;
    }

    public void setDatasourcePassword(String datasourcePassword) {
        this.datasourcePassword = datasourcePassword;
    }

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
        dataSource.setDatasourceCatagory(this.datasourceCategory);
        dataSource.setDatasourceType(this.getDatasourceType());
        dataSource.setDatasourceVersion(this.getDatasourceVersion());
        return dataSource;
    }

    @Override
    public String toString() {
        return "QueryJobInfo{" +
                "serviceId='" + serviceId + '\'' +
                ", serviceNameEn='" + serviceNameEn + '\'' +
                ", serviceNameCn='" + serviceNameCn + '\'' +
                ", version='" + version + '\'' +
                ", serviceDesrciption='" + serviceDesrciption + '\'' +
                ", datasourceId='" + datasourceId + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", sqlTemplate='" + sqlTemplate + '\'' +
                ", countSqlTemplate='" + countSqlTemplate + '\'' +
                ", sqlSupportAutoPagable=" + sqlSupportAutoPagable +
                ", requiredParam='" + requiredParam + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                ", preProcessType='" + preProcessType + '\'' +
                ", preProcessFunc='" + preProcessFunc + '\'' +
                ", postProcessType='" + postProcessType + '\'' +
                ", postProcessFunc='" + postProcessFunc + '\'' +
                ", responseFormat='" + responseFormat + '\'' +
                ", publishStatus='" + publishStatus + '\'' +
                ", datasourceName='" + datasourceName + '\'' +
                ", datasourceDesrciption='" + datasourceDesrciption + '\'' +
                ", pluginPath='" + pluginPath + '\'' +
                ", datasourceCategory='" + datasourceCategory + '\'' +
                ", datasourceType='" + datasourceType + '\'' +
                ", datasourceVersion='" + datasourceVersion + '\'' +
                ", datasourceDriverClass='" + datasourceDriverClass + '\'' +
                ", datasourceUrl='" + datasourceUrl + '\'' +
                ", datasourceUser='" + datasourceUser + '\'' +
                ", datasourcePassword='" + datasourcePassword + '\'' +
                '}';
    }
}
