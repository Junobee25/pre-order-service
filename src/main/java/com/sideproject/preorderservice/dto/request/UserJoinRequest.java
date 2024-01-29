package com.sideproject.preorderservice.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record UserJoinRequest(
        String email,
        String userName,
        String password,
        String memo,
        MultipartFile profilePicture,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}
