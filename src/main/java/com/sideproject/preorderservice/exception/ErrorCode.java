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
    USER_NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "User not authenticated"),
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "Article not founded"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Comment not founded"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "User has invalid permission"),
    INVALID_LIKE_REQUEST(HttpStatus.NOT_FOUND, "Invalid like request");


    private final HttpStatus status;
    private final String message;
}
