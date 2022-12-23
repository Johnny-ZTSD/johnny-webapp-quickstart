package cn.johnnyzen.common.dto;

import cn.johnnyzen.common.dto.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author Johnnyzen
 * @project JohnnyWebappQuickstart
 * @create-time 2022/9/27  01:49:55
 * @description ...
 * @reference-doc
 */

/**
 * 【使用场景示例】(code值的意义不可参考)
 * /login.json?user=admin&paswd=123456
 *  登陆成功 [操作完全成功]
 *      code 200
 *      message 登陆成功
 *      data <userInfo>xxx<userInfo/>
 *      operationExplain null
 *  密码错误 [操作失败情况1]
 *      code 300
 *      message 登陆失败，用户名不正确或密码错误!
 *      data null
 *      operationExplain 用户名存在，但可能是密码填写错误，也可能是用户将用户名填错
 *  账号不存在 [操作失败情况2]
 *      code 404
 *      message 登陆失败，账号不存在
 *      data null
 *      operationExplain null
 *  服务器内部错误 [操作失败情况3]
 *      code 405
 *      message 登陆失败，服务器内部错误!
 *      data null
 *      operationExplain
 *          可能是接口所在服务器确实内部程序错误， [当前接口A所在服务器内部程序故障]
 *          亦可能是接口所在服务器调用第三方服务器失败， [当前接口A调用、封装第三方接口B数据的情况]
 *  登陆失败的其他情况
 *      code 501
 *      message 登陆失败，未知错误!
 *      data null
 *      operationExplain 未知原因的错误，急需修复!
 */
@ApiModel(value = "公共响应对象", description = "分页响应对象") // [swagger] 作用范围: 模型类，如 VO、BO
public class CommonResponse<T> implements Serializable {
    private final static Logger LOG = LoggerFactory.getLogger(BasePage.class);

    private static final long serialVersionUID = 6458385342L;

    @ApiModelProperty(
        value = "响应状态", // swagger 中 的属性说明
        example = "true",
        hidden = false // swagger 中 是否隐藏本属性
    )
    private Boolean status;

    /**
     * @property Entity Data and Exclude Any Tips Message(实体数据，且不包含提示信息)
     * @userObject user(普通用户) ; developer(开发者)
     */
    @ApiModelProperty(value = "业务数据")
    private T data;

    /**
     * @property Status Code(状态代码):
     * @userObject developer(开发者)
     * @desc 既可使用【预定义的枚举状态码(ResultCode)】，也可根据【业务场景使用自定义的状态码】
     *       但为了避免状态码的歧义性，原则上不允许自定义状态码与预定义的枚举码出现重合一致
     *       自定义状态码时，推荐尽量不违反【预定义的枚举状态码】的值域规则
     *       推荐优先使用【预定义的状态码】
     * @initValue defaultValue and Init Flag = -1
     */
    @ApiModelProperty(value = "状态代码")
    private String code;

    /**
     * @property Tips friendly(友好提示信息)
     * @description 类比 errorMessage
     * @userObject user(普通用户) ; [Front-End]developer([前端]开发者)
     * @desc 对操作结果(状态码)进行用户级解释
     *
     */
    @ApiModelProperty(value = "响应信息")
    private String message;

    /**
     * [可选填项属性]
     * @property  the description message about operating failure or exception(操作失败或异常的描述信息)
     * @userObject developer(开发者)
     * @desc 对操作结果/状态码 进行开发级解释，例如：描述导致异常情况的可能原因
     */
    private String operationExplain;

    public CommonResponse(){

    }

    public CommonResponse(Boolean status, T data, String code, String message, String operationExplain) {
        this.status = status;
        this.data = data;
        this.code = code;
        this.message = message;
        this.operationExplain = operationExplain;
    }

    public CommonResponse(Boolean status, T data, String code, String message) {
        this(status, data, code, message, null);
    }

    public static <T> CommonResponse create(Boolean status, T data, String code, String message) {
        return new CommonResponse<T>(status, data, code, message);
    }

    public static <T> CommonResponse success() {
        return create(true, null, null, null);
    }

    public static <T> CommonResponse success(T data) {
        return create(true, data, null, null);
    }

    public static <T> CommonResponse failure() {
        return create(false, null, null, null);
    }

    public static <T> CommonResponse failure(String errorCode) {
        return create(false, null, errorCode, null);
    }

    public static <T> CommonResponse failure(String errorCode, String errorMessage) {
        return create(false, null, errorCode, errorMessage);
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOperationExplain() {
        return operationExplain;
    }

    public void setOperationExplain(String operationExplain) {
        this.operationExplain = operationExplain;
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "status=" + status +
                ", data=" + data +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", operationExplain='" + operationExplain + '\'' +
                '}';
    }
}
