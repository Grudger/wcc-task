package com.wcc.assessment.exception;

import org.springframework.http.HttpStatus;

public class WccException extends RuntimeException {
    private final HttpStatus errorCode;

    public WccException(String message, HttpStatus errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }
}
