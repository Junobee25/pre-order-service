package com.sideproject.preorderservice.dto;

import com.sideproject.preorderservice.domain.entity.UserAccount;

import java.time.LocalDateTime;

public record UserAccountDto(
        Long id,
        String email,
        String userName,
        String userPassword,
        Boolean emailVerified,
        String memo

) {
    public static UserAccountDto of(Long id, String email, String userName, String password, Boolean emailVerified, String memo) {
        return new UserAccountDto(id, email, userName, password, emailVerified, memo);
    }
    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getId(),
                entity.getEmail(),
                entity.getUsername(),
                entity.getUserPassword(),
                entity.getEmailVerified(),
                entity.getMemo()
        );
    }
}
