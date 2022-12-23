package cn.johnnyzen.common.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Johnny
 * @project JohnnyWebappQuickstart
 * @create-time 2022/12/23  21:43:39
 * @description 应用错误异常码
 * @reference-doc
 */

public enum ApplicationErrorCodeEnum {
    QUERY_SQL_IS_EMPTY("QUERY_SQL_IS_EMPTY","查询SQL为空"),
    DATABASE_QUERY_ERROR("DATABASE_QUERY_ERROR", "数据库查询错误")
    ,SYSTEM_INTERNAL_ERROR("SYSTEM_INTERNAL_ERROR", "系统内部异常");

    private String errorCode;

    private String errorMessage;

    ApplicationErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ApplicationErrorCodeEnum findByCode(String code) {
        for (ApplicationErrorCodeEnum type : values()) {
            if (type.getErrorCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        return null;
    }

    public ApplicationErrorCodeEnum findByName(String name) {
        for (ApplicationErrorCodeEnum type : values()) {
            if (type.getErrorMessage().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public static List<Map<String, Object>> toList() {
        //Lists.newArrayList()其实和 new ArrayList() 几乎一模一样
        List<Map<String, Object>> list = new ArrayList();
        for (ApplicationErrorCodeEnum item : ApplicationErrorCodeEnum.values()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", item.getErrorCode());
            map.put("name", item.getErrorMessage());
            list.add(map);
        }
        return list;
    }

    public String getErrorCode(){
        return this.errorCode;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }
}
