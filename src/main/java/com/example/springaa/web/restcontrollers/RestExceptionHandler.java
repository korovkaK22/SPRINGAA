package com.example.springaa.web.restcontrollers;

import com.example.springaa.exceptions.NotAuthorizedException;
import com.example.springaa.exceptions.ForbiddenAccessException;
import com.example.springaa.exceptions.ResourceNotFoundException;
import com.example.springaa.exceptions.RestQueueNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//http://localhost:8080/swagger-ui/index.html#/

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RestQueueNotFoundException.class)
    public ErrorResponse queueNotFound(RestQueueNotFoundException e){
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ErrorResponse notAuthorized(NotAuthorizedException e){
        return ErrorResponse.create(e, HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public ErrorResponse forbiddenAccess(ForbiddenAccessException e){
        return ErrorResponse.create(e, HttpStatus.FORBIDDEN, e.getMessage());
    }
}
