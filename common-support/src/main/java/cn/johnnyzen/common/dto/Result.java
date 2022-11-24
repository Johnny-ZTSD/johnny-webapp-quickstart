package cn.johnnyzen.common.dto;

import java.io.Serializable;

/**
 * @createAuthor Zengtai(千千寰宇)
 * @createDate 2018/9/25  15:19:30
 * @description 统一API响应结果封装
 * @modifiedDate 2020-06-08 17:28
 * @modifiedAuthor Zengtai
 * @modifiedInfo
 *      新增 属性 operationExplain
 *      修改 数据(data)属性的数据类型为泛型
 *      新增 各属性详细的使用解释
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

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * @property Status Code(状态代码):
     * @userObject developer(开发者)
     * @desc 既可使用【预定义的枚举状态码(ResultCode)】，也可根据【业务场景使用自定义的状态码】
     *       但为了避免状态码的歧义性，原则上不允许自定义状态码与预定义的枚举码出现重合一致
     *       自定义状态码时，推荐尽量不违反【预定义的枚举状态码】的值域规则
     *       推荐优先使用【预定义的状态码】
     * @initValue defaultValue and Init Flag = -1
     */
    private int code=-1;

    /**
     * @property Tips friendly(友好提示信息)
     * @userObject user(普通用户) ; [Front-End]developer([前端]开发者)
     * @desc 对操作结果(状态码)进行用户级解释
     *
     */
    private String message = null;

    /**
     * @property Entity Data and Exclude Any Tips Message(实体数据，且不包含提示信息)
     * @userObject user(普通用户) ; developer(开发者)
     */
    private T data = null;

    /**
     * [可选填项属性]
     * @property  the description message about operating failure or exception(操作失败或异常的描述信息)
     * @userObject developer(开发者)
     * @desc 对操作结果/状态码 进行开发级解释，例如：描述导致异常情况的可能原因
     */
    private String operationExplain;

    public Result() {
        super();
    }

    public Result(int code, String message, T data, String operationExplain) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.operationExplain = operationExplain;
    }

    public Result(int code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    /**
     * 通过【预定义的状态码】进行设置code值
     */
    public Result setCode(ResultCodeEnum resultCode) {
        this.code = resultCode.getCode();
        return this;
    }

    /**
     * 通过【自定义的状态码】进行设置code值
     * @return
     */
    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getOperationExplain() {
        return operationExplain;
    }

    public void setOperationExplain(String operationExplain) {
        this.operationExplain = operationExplain;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", operationExplain='" + operationExplain + '\'' +
                '}';
    }
}

