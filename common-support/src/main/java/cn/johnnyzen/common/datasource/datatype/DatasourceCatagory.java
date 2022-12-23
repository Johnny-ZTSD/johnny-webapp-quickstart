package cn.johnnyzen.common.datasource.datatype;

/**
 * 数据源大类枚举
 *
 * @author johnnyzen
 * @since 2022/12/23
 */
public enum DatasourceCatagory {
    DATABASE("database", "database"),
    HTTP("http", "http");

    private String code;
    private String name;

    /**
     *
     * @param code
     * @param name
     */
    DatasourceCatagory(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     *
     * @param code
     * @return
     */
    public static DatasourceCatagory getDsType(String code) {
        for (DatasourceCatagory c : DatasourceCatagory.values()) {
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

