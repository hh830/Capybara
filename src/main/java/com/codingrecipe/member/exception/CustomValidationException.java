package com.codingrecipe.member.exception;

import org.springframework.http.HttpStatus;


public class CustomValidationException extends RuntimeException{
    //private final HttpStatus httpStatus;
    private final int status;

    public CustomValidationException(int status, String message) {
        super(message);
        //this.httpStatus = httpStatus;
        this.status=status;
    }


    public int getStatus() {
        return status;
    }
/*
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }*/
}
