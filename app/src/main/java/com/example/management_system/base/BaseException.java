package com.example.management_system.base;

import java.io.IOException;

public class BaseException extends IOException {

    private String message;
    private int code;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }


    public BaseException(String message) {
        this.message = message;
    }

    public BaseException(String msg, Throwable cause) {
        super(msg, cause);
        this.message = msg;
    }

    public BaseException(int code, String message) {
        this.message = message;
        this.code = code;
    }

}