package com.example.springaa.exceptions;

public class RestQueueNotFoundException extends RuntimeException{
    public RestQueueNotFoundException() {
    }

    public RestQueueNotFoundException(String message) {
        super(message);
    }

    public RestQueueNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestQueueNotFoundException(Throwable cause) {
        super(cause);
    }

    public RestQueueNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
