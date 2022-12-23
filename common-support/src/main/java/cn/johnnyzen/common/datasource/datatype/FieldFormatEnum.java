package cn.johnnyzen.common.datasource.datatype;

import java.util.ArrayList;
import java.util.List;

/**
 * 字段类型定义
 */

public enum FieldFormatEnum {

    C_FIXED("c", "定长unicode字符","定长字符"),
    C_VARIABLE("c..", "变长unicode字符","变长字符"),
    A_FIXED("a", "定长字符仅包含英文字母","定长字符仅包含英文字母"),
    A_VARIABLE("a..", "变长字符仅包含英文字母","变长字符仅包含英文字母"),
    N_FIXED("n", "定长数字","定长数字"),
    N_VARIABLE("n..", "变长数字","变长数字"),
    AN_FIXED("an", "定长字母与数字","定长字母与数字"),
    AN_VARIABLE("an..", "变长字母与数字","变长字母与数字"),
    D_FORMAT("d", "日期","日期"),
    T_FORMAT("t", "时间","时间"),
    N_DECIMAL("n..()", "定长小数","定长小数"),
    DT_FORMAT("dt", "日期时间","日期时间"),
    B_FORMAT("b", "二进制格式","二进制格式");

    private String code;
    private String message;
    private String alaseName;

    private FieldFormatEnum(String code, String message, String alaseName) {
        this.code = code;
        this.message = message;
        this.alaseName = alaseName;
    }

    public static FieldFormatEnum getByCode(String code) {
        for (FieldFormatEnum formatEnum : values()) {
            if(formatEnum.getCode().equals(code)){
                return formatEnum;
            }
        }

        return null;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
    public String getalaseName() {
        return this.alaseName;
    }

    public static List<String> getCodeList() {
        List<String> codes = new ArrayList<String>();
        for (FieldFormatEnum fieldFormatEnum : FieldFormatEnum.values()) {
            codes.add(fieldFormatEnum.getCode());
        }
        return codes;
    }

}
