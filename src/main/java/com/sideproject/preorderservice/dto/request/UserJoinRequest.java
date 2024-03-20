package com.sideproject.preorderservice.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record UserJoinRequest(
        String email,
        String userName,
        String password,
        String memo
) {

    public static UserJoinRequest of(String email, String userName, String password, String memo) {
        return new UserJoinRequest(email, userName, password, memo);
    }
}
