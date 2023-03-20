package com.example.todo.common.exception.error;

import com.example.todo.common.exception.ErrorCode;

public class NotFoundUserException extends RuntimeException{
    public NotFoundUserException(){
        super(ErrorCode.NOT_FOUND_USER.getMessage());
    }
}
