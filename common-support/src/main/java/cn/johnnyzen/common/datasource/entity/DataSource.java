package cn.johnnyzen.common.datasource.entity;

import java.util.Date;
import java.util.Properties;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/11/25 2:19
 * @description 数据源完整信息
 */
public class DataSource extends BaseDataSource {
    public static final String PARAM_DATABASE = "database";
    public static final String PARAM_DATASOURCE_TYPE1 = "datasourceType1";
    public static final String PARAM_DATASOURCE_TYPE2 = "datasourceType2";

    private Long id;
    private String datasourceId;
    private String datasourceName;
    private String datasourceDescription;

    private String pluginPath;

    /**
     * 数据源大类
     * @description 数据源大类、数据源范畴
     * http / database
     */
    private String datasourceCatagory;

    /**
     * 数据源类型
     * @description
     *  datasourceKind=database时:
     *      influxdb / mysql / oracle / postgreSql / clickhouse / elasticsearch / ...
     */
    private String datasourceType;

    private String datasourceVersion;

    /**
     * 当前选中的数据库
     * @description
     *  非数据库持久化字段
     *  [非必需字段] 仅部分数据源建立连接时可能使用
     */
    private String database;

    /**
     * 数据保留策略
     * @description
     *  非数据库持久化字段
     *  [非必需字段] 仅部分数据源建立连接时可能使用
     */
    private String retentionPolicy;

    /**
     * 其他的扩展配置属性
     */
    private Properties otherProperties;

    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;

    private Boolean isDelete;

    public DataSource(){

    }

    public DataSource(String datasourceDriverClass, String datasourceUrl, String datasourceUser, String datasourcePassword) {
        this.datasourceDriverClass = datasourceDriverClass;
        this.datasourceUrl = datasourceUrl;
        this.datasourceUser = datasourceUser;
        this.datasourcePassword = datasourcePassword;
    }

    public DataSource(
            String datasourceDriverClass
            , String datasourceUrl
            , String datasourceUser
            , String datasourcePassword
            , String database
    ) {
        this.datasourceDriverClass = datasourceDriverClass;
        this.datasourceUrl = datasourceUrl;
        this.datasourceUser = datasourceUser;
        this.datasourcePassword = datasourcePassword;
        this.database = database;
    }

    public DataSource(
            String datasourceUrl
            , String datasourceUser
            , String datasourcePassword
            , Properties otherProperties
    ){
        this.datasourceUrl = datasourceUrl;
        this.datasourceUser = datasourceUser;
        this.datasourcePassword = datasourcePassword;
        this.otherProperties = otherProperties;
    }

    public DataSource(String datasourceUrl, String datasourceUser, String datasourcePassword) {
        this(datasourceUrl, datasourceUser, datasourcePassword, new Properties());
    }


    public Long getId() {
        return id;
    }

    public DataSource setId(Long id) {
        this.id = id;
        return this;
    }



    public String getDatasourceId() {
        return datasourceId;
    }

    public DataSource setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
        return this;
    }

    public String getDatasourceName() {
        return datasourceName;
    }

    public DataSource setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName;
        return this;
    }

    public String getDatasourceDescription() {
        return datasourceDescription;
    }

    public DataSource setDatasourceDescription(String datasourceDescription) {
        this.datasourceDescription = datasourceDescription;
        return this;
    }

    public String getPluginPath() {
        return pluginPath;
    }

    public DataSource setPluginPath(String pluginPath) {
        this.pluginPath = pluginPath;
        return this;
    }

    public String getDatasourceCatagory() {
        return datasourceCatagory;
    }

    public void setDatasourceCatagory(String datasourceCatagory) {
        this.datasourceCatagory = datasourceCatagory;
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

    public DataSource setDatasourceVersion(String datasourceVersion) {
        this.datasourceVersion = datasourceVersion;
        return this;
    }

    public String getCreateBy() {
        return createBy;
    }

    public DataSource setCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public DataSource setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public DataSource setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public DataSource setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public DataSource setDelete(Boolean delete) {
        isDelete = delete;
        return this;
    }

    public String getDatabase() {
        return database;
    }

    public DataSource setDatabase(String database) {
        this.database = database;
        return this;
    }

    public String getRetentionPolicy() {
        return retentionPolicy;
    }

    public DataSource setRetentionPolicy(String retentionPolicy) {
        this.retentionPolicy = retentionPolicy;
        return this;
    }

    public Properties getOtherProperties() {
        return otherProperties;
    }

    public DataSource setOtherProperties(Properties otherProperties) {
        this.otherProperties = otherProperties;
        return this;
    }



    @Override
    public String toString() {
        return "DataSource{" +
                "id=" + id +
                ", datasourceId='" + datasourceId + '\'' +
                ", datasourceName='" + datasourceName + '\'' +
                ", datasourceDescription='" + datasourceDescription + '\'' +
                ", pluginPath='" + pluginPath + '\'' +
                ", datasourceCatagory='" + datasourceCatagory + '\'' +
                ", datasourceType='" + datasourceType + '\'' +
                ", datasourceVersion='" + datasourceVersion + '\'' +
                ", datasourceDriverClass='" + datasourceDriverClass + '\'' +
                ", datasourceUrl='" + datasourceUrl + '\'' +
                ", datasourceUser='" + datasourceUser + '\'' +
//                ", datasourcePassword='" + datasourcePassword + '\'' +
                ", database='" + database + '\'' +
                ", retentionPolicy='" + retentionPolicy + '\'' +
                ", otherProperties='" + otherProperties + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", isDelete=" + isDelete +
                '}';
    }
}
