package com.bootcamp.demo.exception;
import com.bootcamp.demo.business.AccountService.ErrorCode;

public class ServiceException extends Exception{
    private final ErrorCode code;
    public ServiceException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }

    public ErrorCode getErrorCode() {
        return code;
    }
}
