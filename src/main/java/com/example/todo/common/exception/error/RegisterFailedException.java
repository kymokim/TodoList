package com.example.todo.common.exception.error;

import com.example.todo.common.exception.ErrorCode;

public class RegisterFailedException extends RuntimeException{
    public RegisterFailedException(){
        super(ErrorCode.AUTHENTICATION_CONFLICT.getMessage());
    }
}