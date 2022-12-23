package cn.johnnyzen.common.datasource.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/12/1 18:42
 * @description 数据源基本信息
 */
public class BaseDataSource {
    public static final String PARAM_DATASOURCE_ID = "datasourceId";
    public static final String PARAM_URL = "url";
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWORD = "password";

    /**
     * 数据库连接的公共属性: 驱动类类名 [非必需]
     */
    protected String datasourceDriverClass;

    /**
     * 数据库连接的公共属性: 数据库连接的URL [必填]
     */
    protected String datasourceUrl;

    /**
     * 数据库连接的公共属性: 数据库连接的用户名 [非必填]
     */
    protected String datasourceUser;

    /**
     * 数据库连接的公共属性: 数据库连接的密码 [非必填]
     */
    @JsonIgnore
    protected String datasourcePassword;

    public BaseDataSource() {

    }

    public BaseDataSource(String datasourceDriverClass, String datasourceUrl, String datasourceUser, String datasourcePassword) {
        this.datasourceDriverClass = datasourceDriverClass;
        this.datasourceUrl = datasourceUrl;
        this.datasourceUser = datasourceUser;
        this.datasourcePassword = datasourcePassword;
    }

    public String getDatasourceDriverClass() {
        return datasourceDriverClass;
    }

    public BaseDataSource setDatasourceDriverClass(String datasourceDriverClass) {
        this.datasourceDriverClass = datasourceDriverClass;
        return this;
    }

    public String getDatasourceUrl() {
        return datasourceUrl;
    }

    public BaseDataSource setDatasourceUrl(String datasourceUrl) {
        this.datasourceUrl = datasourceUrl;
        return this;
    }

    public String getDatasourceUser() {
        return datasourceUser;
    }

    public BaseDataSource setDatasourceUser(String datasourceUser) {
        this.datasourceUser = datasourceUser;
        return this;
    }



    public String getDatasourcePassword() {
        return datasourcePassword;
    }

    public BaseDataSource setDatasourcePassword(String datasourcePassword) {
        this.datasourcePassword = datasourcePassword;
        return this;
    }

    @Override
    public String toString() {
        return "BaseDataSource{" +
                "datasourceDriverClass='" + datasourceDriverClass + '\'' +
                ", datasourceUrl='" + datasourceUrl + '\'' +
                ", datasourceUser='" + datasourceUser + '\'' +
                ", datasourcePassword='" + datasourcePassword + '\'' +
                '}';
    }
}
