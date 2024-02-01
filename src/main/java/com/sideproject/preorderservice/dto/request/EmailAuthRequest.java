package com.sideproject.preorderservice.dto.request;

public record EmailAuthRequest(
        String email,
        String authToken
) {
}
