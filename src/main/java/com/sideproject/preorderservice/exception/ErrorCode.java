package com.sideproject.preorderservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "User name is duplicated"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Password is invalid"),
    EMAIL_AUTH_TOKEN_NOT_FOUNT(HttpStatus.NOT_FOUND, "Invalid email request"),
    USER_NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "User not authenticated");

    private final HttpStatus status;
    private final String message;
}