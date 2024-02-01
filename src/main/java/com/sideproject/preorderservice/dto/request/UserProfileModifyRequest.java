package com.sideproject.preorderservice.dto.request;

public record UserProfileModifyRequest(
        String userName,
        String memo,
        String profilePicture
) {
}
