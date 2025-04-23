package com.example.frogcrew.system;

import org.springframework.http.HttpStatus;

public class Result {
    private boolean success;
    private HttpStatus status;
    private String message;
    private Object data;

    public Result(boolean success, HttpStatus status, String message, Object data) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Result(boolean success, HttpStatus status, String message) {
        this(success, status, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

