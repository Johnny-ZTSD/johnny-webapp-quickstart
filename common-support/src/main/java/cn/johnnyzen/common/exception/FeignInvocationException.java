package cn.johnnyzen.common.exception;

import java.util.ArrayList;
import java.util.List;

public class FeignInvocationException extends RuntimeException {

    private final List<String> messages = new ArrayList();

    private String code;

    private String errorId;

    private String errorDetail;

    public FeignInvocationException(String code,String errorId,String errorDetail,List<String> messages){
        super(String.format("%s:{%s}",code, null != messages ? String.join(";", messages):""));
        this.code = code;
        this.errorId = errorId;
        this.errorDetail = errorDetail;
        if(null != messages){
            this.messages.addAll(messages);
        }
    }

    public FeignInvocationException(String code,String errorId,String errorDetail,String messages){
        super(String.format("%s:{%s}",code,messages));
        this.code = code;
        this.errorId = errorId;
        this.errorDetail = errorDetail;
        if(null != messages){
            this.messages.add(messages);
        }
    }

    public String getMessages() {
        return String.join(";", messages);
    }

    public String getCode() {
        return code;
    }

    public String getErrorId() {
        return errorId;
    }

    public String getErrorDetail() {
        return errorDetail;
    }
}

