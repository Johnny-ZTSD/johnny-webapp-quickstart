package cn.johnnyzen.common.dto;

/**
 * @IDE: Created by IntelliJ IDEA.
 * @Author: Zengtai(千千寰宇)
 * @Date: 2018/9/30  21:03:59
 * @modifiedDate 2020-06-09 09:41
 * @modifiedAuthor Zengtai
 * @modifiedInfo 对Result的新增字段operationExplain进行方法的兼容适配
 */

public class ResultUtil {

    public static Result success(String message, Object data){
        return template(ResultCodeEnum.SUCCESS, message, data);
    }

    public static Result success(Object data){
        return success(ResultCodeEnum.SUCCESS.getExplain(), data);
    }

    public static Result success(String message){
        return success( message, null);
    }

    public static Result success() {
        return success(ResultCodeEnum.SUCCESS.getExplain(), null);
    }

    public static Result error(ResultCodeEnum code, String message, Object data, String operationExplain) {
        return template(code, message, data, operationExplain);
    }

    public static Result error(ResultCodeEnum code, String message, Object data) {
        return error(code, message, data, code.getExplain());
    }

    public static Result error(ResultCodeEnum code, String message, String operationExplain) {
        return error(code, message, null, operationExplain);
    }

    public static Result error(ResultCodeEnum code, Object data,String operationExplain) {
        return error(code, code.getExplain() , data, operationExplain);
    }

    public static Result error(String message, Object data, String operationExplain) {
        return error(ResultCodeEnum.FAIL, message, data, operationExplain);
    }

    public static Result error(ResultCodeEnum code, String message) {
        return error(code, message,null, code.getExplain());
    }

    public static Result error(ResultCodeEnum code, Object data) {
        return error(code, code.getExplain(), data, code.getExplain());
    }

    public static Result error(String message, Object data) {
        return error(ResultCodeEnum.FAIL, message, data, ResultCodeEnum.FAIL.getExplain());
    }

    public static Result error(String message, String operationExplain) {
        return error(ResultCodeEnum.FAIL, message, null, operationExplain);
    }

    public static Result error(Object data, String operationExplain) {
        return error(ResultCodeEnum.FAIL, ResultCodeEnum.FAIL.getExplain(), data, operationExplain);
    }

    //加入 Exception 作为 operationExplain
    public static Result error(ResultCodeEnum code, Exception exception) {
        return template(code, ResultCodeEnum.FAIL.getExplain(), null, exception.getMessage());
    }

    public static Result template(ResultCodeEnum code, String message, Object data, String operationExplain){
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        result.setOperationExplain(operationExplain);
        return  result;
    }

    public static Result template(ResultCodeEnum code, String message, Object data){
        return template(code, message,data, code.getExplain());
    }
}
