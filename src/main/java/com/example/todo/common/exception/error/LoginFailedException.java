package com.example.todo.common.exception.error;

import com.example.todo.common.exception.ErrorCode;

public class LoginFailedException extends RuntimeException{
    public LoginFailedException(){
        super(ErrorCode.LOGIN_FAILED.getMessage());
    }
}
