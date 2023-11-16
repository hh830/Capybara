package com.codingrecipe.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;



public class CustomValidationException extends RuntimeException{
    private final int status;

    public CustomValidationException(int status, String message) {
        super(message);
        this.status = status;
    }


    public int getStatus() {
        return status;
    }
}
