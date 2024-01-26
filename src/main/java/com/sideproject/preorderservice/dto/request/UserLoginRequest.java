package com.sideproject.preorderservice.dto.request;

public record UserLoginRequest(
        String email,
        String password
) {
}
