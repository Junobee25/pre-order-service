package com.sideproject.preorderservice.dto.response;

public record UserLoginResponse(
        String accessToken,
        String refreshToken
) {
}
