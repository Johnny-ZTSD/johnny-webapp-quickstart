package cn.johnnyzen.common.datasource.datatype;

/**
 * 数据源类型枚举
 * @description DatasourceCategory 的子类
 * @author johnnyzen
 * @since 2022/12/23
 */
public enum DatasourceType {
    INFLUXDB("influxdb", "influxdb"),
    KAFKA("kafka", "kafka"),
    MYSQL("mysql", "mysql"),
    CLICKHOUSE("clickhouse", "ClickHouse"),

    ELASTICSEARCH("elasticsearch", "ElasticSearch"),
    REDIS("redis", "Redis");


    private String code;
    private String name;

    /**
     *
     * @param code
     * @param name
     */
    DatasourceType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     *
     * @param code
     * @return
     */
    public static DatasourceType getDsType(String code) {
        for (DatasourceType c : DatasourceType.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     */
    void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    void setName(String name) {
        this.name = name;
    }
}

