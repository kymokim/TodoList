package com.example.todo.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    AUTHENTICATION_CONFLICT(HttpStatus.CONFLICT,"AUTH__009"," AUTHENTICATION_CONFLICT."),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED,"AUTH__001"," LOGIN_FAILED"),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND,"USER_001","NOT_FOUND_USER"),
    LOGIN_FAILED(HttpStatus.NOT_FOUND,"AUTH_002","LOGIN_FAILED.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus httpStatus, final String code, final String message){
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
