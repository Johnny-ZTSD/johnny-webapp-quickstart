package cn.johnnyzen.common.datasource.datatype;

/**
 * @author johnnyzen
 * @version v1.0
 * @create-time 2022/10/8 15:49
 * @description 各数据库的数据类型与Java类型的映射关系
 */
public enum DataTypeEnum {
    INFLUXDB_STRING(DatasourceType.INFLUXDB.getCode(), "STRING", String.class),
    INFLUXDB_INTEGER(DatasourceType.INFLUXDB.getCode(), "INTEGER", Integer.class),
    INFLUXDB_LONG(DatasourceType.INFLUXDB.getCode(), "LONG", Long.class),
    INFLUXDB_FLOAT(DatasourceType.INFLUXDB.getCode(), "FLOAT", Float.class),
    INFLUXDB_BOOLEAN(DatasourceType.INFLUXDB.getCode(), "BOOLEAN", Boolean.class);

    private final String databaseType;
    private final String dbDataType;
    private final Class javaDataType;

    DataTypeEnum(String databaseType, String dbDataType, Class javaDataType){
        this.databaseType = databaseType;
        this.dbDataType = dbDataType;
        this.javaDataType = javaDataType;
    }

    public static <T> DataTypeEnum findDBDataType(String databaseType, Class<T> javaDataType) {
        for (DataTypeEnum type : values()) {
            if (type.databaseType.equalsIgnoreCase(databaseType) && type.javaDataType.equals(javaDataType)) {
                return type;
            }
        }
        return null;
    }

    public String getDatabaseType() {
        return this.databaseType;
    }

    public String getDbDataType() {
        return this.dbDataType;
    }

    public Class getJavaDataType() {
        return this.getJavaDataType();
    }

    public static DataTypeEnum findJavaDataType(String databaseType, String dbDataType) {
        for (DataTypeEnum type : values()) {
            if (type.databaseType.equalsIgnoreCase(databaseType) && type.dbDataType.equalsIgnoreCase(dbDataType)) {
                return type;
            }
        }
        return null;
    }
}
