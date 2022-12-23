package cn.johnnyzen.common.exception;

import cn.johnnyzen.common.dto.ResponseCodeEnum;

/**
 * @usage
 *  throw new ApplicationRuntimeException(LocalMessageUtil.get("common.handler.DispatchTaskHandler.deleteTask.error") + e.getMessage());
 */
public class ApplicationRuntimeException extends RuntimeException {
    private String errorMessage;
    private String errorCode;

    public ApplicationRuntimeException(String errorMsg) {
        super(errorMsg);
        this.errorMessage = errorMsg;
    }

    public ApplicationRuntimeException(String errorMsgKey, Object... values) {
        super(String.format(errorMsgKey, values));
        this.errorMessage = String.format(errorMsgKey, values);
    }

    public ApplicationRuntimeException(Throwable cause){
        super(cause);
    }

    public ApplicationRuntimeException(Throwable cause, String errorMsg, String errorCode) {
        super(errorMsg, cause);
        this.errorMessage = errorMsg;
        this.errorCode = errorCode;
    }

    public ApplicationRuntimeException(ResponseCodeEnum errorCodeEnum){
        this.errorCode = String.valueOf(errorCodeEnum.getCode());
        this.errorMessage = errorCodeEnum.getName();
    }

    public ApplicationRuntimeException(String errorMessage, String errorCode){
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "ApplicationRuntimeException{" +
                "errorMessage='" + errorMessage + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
