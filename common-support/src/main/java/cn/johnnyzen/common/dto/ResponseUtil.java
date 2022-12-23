package cn.johnnyzen.common.dto;

/**
 * @author johnnyzen
 * @date 2018/9/30  21:03:59
 * @modifiedDate 2020-06-09 09:41
 * @modifiedInfo 对Result的新增字段operationExplain进行方法的兼容适配
 */

public class ResponseUtil {

    public static CommonResponse success(String message, Object data){
        return template(ResponseCodeEnum.SUCCESS, message, data);
    }

    public static CommonResponse success(Object data){
        return success(ResponseCodeEnum.SUCCESS.getExplain(), data);
    }

    public static CommonResponse success(String message){
        return success( message, null);
    }

    public static CommonResponse success() {
        return success(ResponseCodeEnum.SUCCESS.getExplain(), null);
    }

    public static CommonResponse error(ResponseCodeEnum code, String message, Object data, String operationExplain) {
        return template(code, message, data, operationExplain);
    }

    public static CommonResponse error(ResponseCodeEnum code, String message, Object data) {
        return error(code, message, data, code.getExplain());
    }

    public static CommonResponse error(ResponseCodeEnum code, String message, String operationExplain) {
        return error(code, message, null, operationExplain);
    }

    public static CommonResponse error(ResponseCodeEnum code, Object data, String operationExplain) {
        return error(code, code.getExplain() , data, operationExplain);
    }

    public static CommonResponse error(String message, Object data, String operationExplain) {
        return error(ResponseCodeEnum.FAIL, message, data, operationExplain);
    }

    public static CommonResponse error(ResponseCodeEnum code, String message) {
        return error(code, message,null, code.getExplain());
    }

    public static CommonResponse error(ResponseCodeEnum code, Object data) {
        return error(code, code.getExplain(), data, code.getExplain());
    }

    public static CommonResponse error(String message, Object data) {
        return error(ResponseCodeEnum.FAIL, message, data, ResponseCodeEnum.FAIL.getExplain());
    }

    public static CommonResponse error(String message, String operationExplain) {
        return error(ResponseCodeEnum.FAIL, message, null, operationExplain);
    }

    public static CommonResponse error(Object data, String operationExplain) {
        return error(ResponseCodeEnum.FAIL, ResponseCodeEnum.FAIL.getExplain(), data, operationExplain);
    }

    //加入 Exception 作为 operationExplain
    public static CommonResponse error(ResponseCodeEnum code, Exception exception) {
        return template(code, ResponseCodeEnum.FAIL.getExplain(), null, exception.getMessage());
    }

    public static CommonResponse template(ResponseCodeEnum code, String message, Object data, String operationExplain){
        CommonResponse result = new CommonResponse();
        result.setCode(String.valueOf(code.getCode()));

        //重新定义 code 对应的  message
        result.setMessage(message);

        result.setOperationExplain(operationExplain);
        result.setData(data);
        result.setOperationExplain(operationExplain);
        return  result;
    }

    public static CommonResponse template(ResponseCodeEnum code, String message, Object data){
        return template(code, message,data, code.getExplain());
    }
}
