package com.supience.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
} 