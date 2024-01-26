package com.sideproject.preorderservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PreOrderApplicationException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public PreOrderApplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;
    }

    @Override
    public String getMessage() {
        if (message == null) {
            return errorCode.getMessage();
        } else {
            return String.format("%s. %s", errorCode.getMessage(), message);
        }
    }
}
