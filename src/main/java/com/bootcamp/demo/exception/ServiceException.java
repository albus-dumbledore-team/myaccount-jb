package com.bootcamp.demo.exception;

public class ServiceException extends Exception{
    private final ErrorCode code;
    public ServiceException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }

    public ErrorCode getErrorCode() {
        return code;
    }

    public enum ErrorCode{
        INTERNAL(1),
        VALIDATION(2);
        private final int errorCode;
        ErrorCode(int i) {
            this.errorCode = i;
        }

        public int getErrorCode() {
            return errorCode;
        }
    }
}
