package com.microservice.gatewayserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RoleMismatchExceptionHandler extends RuntimeException{
    public RoleMismatchExceptionHandler(String message){
        super(message);
    }
}
