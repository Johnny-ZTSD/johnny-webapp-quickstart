package cn.johnnyzen.common.exception;

import java.util.List;

public class ErrorMessagesException extends RuntimeException {

    private List<String> errors;
    public ErrorMessagesException(List<String> messages) {
        super(String.join(";", messages));
        this.errors = messages;
    }

    public List<String> getErrors(){
        return errors;
    }

}
