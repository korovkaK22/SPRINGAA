package com.example.springaa.exceptions;

public class ForbiddenAccessException extends RuntimeException{
    public ForbiddenAccessException() {
        super();
    }

    public ForbiddenAccessException(String message) {
        super(message);
    }

    public ForbiddenAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenAccessException(Throwable cause) {
        super(cause);
    }

    protected ForbiddenAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
