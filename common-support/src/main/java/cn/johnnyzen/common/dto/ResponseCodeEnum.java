package cn.johnnyzen.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 千千寰宇
 * @createDate 2018/9/30  18:52:48
 * @modifyDate
 *  2020-06-06 16:37
 *      更换代码值及其意义
 *      实现Serializable接口
 * @description: ...
 */

public enum ResponseCodeEnum implements Serializable {
    /**
     * @description 操作符合操作预期
     * 2000-2999
     */
    SUCCESS(2000, "SUCCESS", "操作成功"),//[概括性地描述，概括范围 2001-2999]

    /**
     * @description 操作失败，且操作不符合预期而导致的操作失败 (服务器正常运行)
     * 4000-4999
     */
    FAIL(4000, "FAIL", "操作失败或执行异常"),//[概括性地描述，概括范围 4001-4999]
    REQUEST_NOT_SUPPORT_ERROR(4100, "REQUEST_NOT_SUPPORT_ERROR","请求的服务不支持错误"),
    REQUEST_NOT_FOUND_ERROR(4101, "REQUEST_NOT_FOUND_ERROR","请求的服务不存在错误"),
    REQUEST_NO_DEVELOP_OR_DEVELOPING_ERROR(4102, "REQUEST_NO_DEVELOP_OR_DEVELOPING_ERROR","请求的服务成功，但尚未或尚在开发中"),
    PARAMETERS_NOT_VALID(4200, "PARAMETERS_NOT_VALID","参数无效"),
    PARAMETERS_UNCOMPLETE_ERROR(4201, "PARAMETERS_UNCOMPLETE_ERROR","参数不完整(少参)"),
    PARAMETERS_TYPE_OR_FORMAT_ERROR(4202, "PARAMETERS_TYPE_OR_FORMAT_ERROR","多参数中存在参数类型或参数格式错误"),
    //特设 认证类型枚举[4400-4499]
    AUTHORIZE_ERROR(4400,"AUTHORIZE_ERROR","认证错误"),
    UNAUTHORIZED_ERROR(4401, "UNAUTHORIZED_ERROR","未认证(签名错误)"),
    AUTHORIZE_SUCCESS(4402,"AUTHORIZE_SUCCESS","认证通过"),
    AUTHORIZE_FAIL(4403,"AUTHORIZE_FAIL","认证失败"),
    //特设 用户枚举[4700-4799]
    USER_LOGIN_ERROR(4700,"USER_LOGIN_ERROR","用户登录类型错误"), //[概括性地描述，概括范围 4500-4599]
    NOT_LOGIN_NO_ACCESS(4701, "NOT_LOGIN_NO_ACCESS","未登录不能访问"),
    USERNAME_ERROR_OR_PASSWORD_ERROR(4702, "USERNAME_ERROR_OR_PASSWORD_ERROR","用户名或密码错误"),
    VERIFICATION_CODE_ERROR(4703, "VERIFICATION_CODE_ERROR","验证码错误"),//手机验证码、邮箱验证码、图形图像识别验证码等

    /**
     * @description 操作失败，且由服务器级故障(自身服务器或第三方服务器)引起
     * 5000-5999
     */
    SERVER_INTERNAL_ERROR(5000,"SERVER_INTERNAL_ERROR","服务器内部错误"),//[概括性地描述，概括范围 5001-5999]
    SERVER_MAINTAINING_ERROR(5001, "SERVER_MAINTAINING_ERROR","服务器维护中"),
    //特设 网络类型故障枚举[5200-5299]
    SERVER_NET_ERROR(5200, "SERVER_NET_ERROR","网络故障类错误"),//[概括性地描述，概括范围 5200-5299]
    //特设 数据库类型故障枚举[5300-5399]
    SERVER_DB_ERROR(5300, "SERVER_DB_ERROR","数据库类错误"),//[概括性地描述，概括范围 5300-5399]
    //特设 IO类型故障枚举[5400-5499]
    SERVER_IO_ERROR(5400, "SERVER_IO_ERROR","文件或数据读写类错误"),//[概括性地描述，概括范围 5400-5499]
    //特设 第三方服务器枚举[5500-5599]
    THIRD_SYSTEM_ERROR(5500, "THIRD_SYSTEM_ERROR","第三方服务器故障引起的操作失败"),//[概括性地描述，概括范围 5500-5599]

    /**
     * @description 未知类型错误
     * 6000-6999
     */
    UNKNOWN_ERROR(6000, "UNKNOWN_ERROR","未知型错误");//[概括性地描述，概括范围 6001-6999]

    /**
     * @property Status Code(状态码)
     * @initData default Value is -1
     */
    private int code=-1;

    /**
     * @description 状态码的英文名，对应 CommonResponse.message
     */
    private String name;

    /**
     * @description 对状态码的业务解释，对应 CommonResponse.operationExplain
     */
    public String explain;

    ResponseCodeEnum(int code, String name, String explain) {
        this.code = code;
        this.name = name;
        this.explain = explain;
    }

    public ResponseCodeEnum findByCode(int code) {
        for (ResponseCodeEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }

    public ResponseCodeEnum findByName(String name) {
        for (ResponseCodeEnum type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public static List<Map<String, Object>> toList() {
        //Lists.newArrayList()其实和 new ArrayList() 几乎一模一样
        List<Map<String, Object>> list = new ArrayList();
        for (ResponseCodeEnum item : ResponseCodeEnum.values()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", item.getCode());
            map.put("name", item.getName());
            list.add(map);
        }
        return list;
    }

    public int getCode(){
        return this.code;
    }

    public String getName(){
        return this.explain;
    }

    public String getExplain(){
        return this.explain;
    }
}
