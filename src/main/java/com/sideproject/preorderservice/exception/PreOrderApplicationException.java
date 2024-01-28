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

    //TODO: 원래 Enum으로 정의해서 예외 처리를 해주려 했는데 이메일 인증 예외 처리 과정에서 orElseThrow를 사용해야 되서 기본 생성자를 정의해서 사용함 리팩토링 필요
    public PreOrderApplicationException() {

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
