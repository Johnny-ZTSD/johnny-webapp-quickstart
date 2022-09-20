package cn.johnnyzen.common.exception;

/**
 * @usage
 *  throw new BusinessException(LocalMessageUtil.get("common.handler.DispatchTaskHandler.deleteTask.error") + e.getMessage());
 */
public class BusinessException extends RuntimeException {

    private String errorMsg;
    private String errorCode;

    public BusinessException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public BusinessException(String errorMsgKey, Object... values) {
        super(String.format(errorMsgKey, values));
        this.errorMsg = String.format(errorMsgKey, values);
    }

    public BusinessException(Throwable cause, String errorMsg, String errorCode) {
        super(errorMsg, cause);
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "errorMsg='" + errorMsg + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
