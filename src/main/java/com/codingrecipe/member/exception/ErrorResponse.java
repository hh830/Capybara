package com.codingrecipe.member.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus(){
        return status;
    }
    public void setStatus(){
        this.status=status;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
