package cn.johnnyzen.common.datasource.datatype;

/**
 * 基础标准字段类型
 */
public enum BaseStandardType {
    DBCLOB("dbclob", FieldFormatEnum.C_VARIABLE),
    RECFLOAT("recfloat", FieldFormatEnum.N_DECIMAL),
    VARGRAPHIC("vargraphic", FieldFormatEnum.C_VARIABLE),
    GRAPHIC("graphic", FieldFormatEnum.C_FIXED),
    DATE("date", FieldFormatEnum.D_FORMAT),
    INTERVAL("interval", FieldFormatEnum.T_FORMAT),
    TIME("time", FieldFormatEnum.T_FORMAT),
    RELTIME("reltime", FieldFormatEnum.T_FORMAT),
    TIMEZ("timez", FieldFormatEnum.T_FORMAT),
    DATETIME("datetime", FieldFormatEnum.DT_FORMAT),
    DATETIME2("datetime2", FieldFormatEnum.DT_FORMAT),
    DATETIMEOFFSET("datetimeoffset", FieldFormatEnum.DT_FORMAT),
    TIMESTAMP("timestamp", FieldFormatEnum.DT_FORMAT),
    TIMESTAMPTZ("timestamptz", FieldFormatEnum.DT_FORMAT),
    YEAR("year", FieldFormatEnum.D_FORMAT),
    SMALLDATETIME("smalldatetime", FieldFormatEnum.DT_FORMAT),
    BIT("bit", FieldFormatEnum.N_FIXED),
    BOOL("bool", FieldFormatEnum.C_VARIABLE),
    BOOLEAN("boolean", FieldFormatEnum.C_VARIABLE),
    SMALLINT("smallint", FieldFormatEnum.N_VARIABLE),
    MEDIUMINT("mediumint", FieldFormatEnum.N_VARIABLE),
    TINYINT("tinyint", FieldFormatEnum.N_VARIABLE),
    INT("int", FieldFormatEnum.N_VARIABLE),
    INTEGER("integer", FieldFormatEnum.N_VARIABLE),
    BIGINT("bigint", FieldFormatEnum.N_VARIABLE),
    BINARY_INTEGER("binary integer", FieldFormatEnum.N_VARIABLE),
    INT1("int1", FieldFormatEnum.N_VARIABLE),
    INT2("int2", FieldFormatEnum.N_VARIABLE),
    INT4("int4", FieldFormatEnum.N_VARIABLE),
    INT8("int8", FieldFormatEnum.N_VARIABLE),
    SMALLSERIAL("smallserial", FieldFormatEnum.N_VARIABLE),
    SERIAL("serial", FieldFormatEnum.N_VARIABLE),
    BIGSERIAL("bigserial", FieldFormatEnum.N_VARIABLE),
    NUMBER("number", FieldFormatEnum.N_DECIMAL),
    DECIMAL("decimal", FieldFormatEnum.N_DECIMAL),
    NUMERIC("numeric", FieldFormatEnum.N_DECIMAL),
    DEC("dec", FieldFormatEnum.N_DECIMAL),
    FLOAT("float", FieldFormatEnum.N_DECIMAL),
    FLOAT4("float4", FieldFormatEnum.N_DECIMAL),
    FLOAT8("float8", FieldFormatEnum.N_DECIMAL),
    DOUBLE("double", FieldFormatEnum.N_DECIMAL),
    DOUBLE_PRECISION("double precision", FieldFormatEnum.N_DECIMAL),
    BINARY_DOUBLE("binary double", FieldFormatEnum.N_DECIMAL),
    SMALLMONEY("smallmoney", FieldFormatEnum.N_DECIMAL),
    REAL("real", FieldFormatEnum.N_DECIMAL),
    MONEY("money", FieldFormatEnum.N_DECIMAL),
    VARCHAR("varchar", FieldFormatEnum.C_VARIABLE),
    CHARACTER_VARYING("charcater varying", FieldFormatEnum.C_VARIABLE),
    VARCHAR2("varchar2", FieldFormatEnum.C_VARIABLE),
    NVARCHAR2("nvarchar2", FieldFormatEnum.C_VARIABLE),
    BYTE("byte", FieldFormatEnum.C_VARIABLE),
    TEXT("text", FieldFormatEnum.C_VARIABLE),
    BYTEA("bytea", FieldFormatEnum.C_VARIABLE),
    STRING("string", FieldFormatEnum.C_VARIABLE),
    LONGVARCHAR("longvarchar", FieldFormatEnum.C_VARIABLE),
    CLOB("clob", FieldFormatEnum.C_VARIABLE),
    RAW("raw", FieldFormatEnum.B_FORMAT),
    CHAR("char", FieldFormatEnum.C_FIXED),
    NCHAR("nchar", FieldFormatEnum.C_FIXED),
    CHARACTER("character", FieldFormatEnum.C_FIXED),
    NVARCHAR("nvarchar", FieldFormatEnum.C_VARIABLE),
    NTEXT("ntext", FieldFormatEnum.C_VARIABLE),
    MACADDR("macaddr", FieldFormatEnum.C_VARIABLE),
    BINARY("binary", FieldFormatEnum.B_FORMAT),
    VARBINARY("varbinary", FieldFormatEnum.B_FORMAT),
    LONGVARBINARY("longvarbinary", FieldFormatEnum.B_FORMAT),
    IMAGE("image", FieldFormatEnum.B_FORMAT),
    TINYTEXT("tinytext", FieldFormatEnum.C_VARIABLE),
    MEDIUMTEXT("mediumtext", FieldFormatEnum.C_VARIABLE),
    LONGTEXT("longtext", FieldFormatEnum.C_VARIABLE),
    BLOB("blob", FieldFormatEnum.B_FORMAT),
    LONGBLOB("longblob", FieldFormatEnum.B_FORMAT),
    LONG("long", FieldFormatEnum.C_VARIABLE),
    LONG_RAW("long raw", FieldFormatEnum.B_FORMAT),
    NCLOB("nclob", FieldFormatEnum.C_VARIABLE),
    BFILE("bfile", FieldFormatEnum.B_FORMAT),
    ROWID("rowid", FieldFormatEnum.C_FIXED),
    BPCHAR("bpchar", FieldFormatEnum.C_FIXED),
    TIMETZ("timetz", FieldFormatEnum.T_FORMAT);

    private String code;
    private FieldFormatEnum type;

    BaseStandardType(String code, FieldFormatEnum type) {
        this.code = code;
        this.type = type;
    }

    public static BaseStandardType getByCode(String code) {
        for (BaseStandardType type : values()) {
            if (type.getCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }

    public FieldFormatEnum getType() {
        return this.type;
    }


}

