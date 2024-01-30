package com.sideproject.preorderservice.dto;

import com.sideproject.preorderservice.domain.UserAccount;

import java.time.LocalDateTime;

public record UserAccountDto(
        Long id,
        String email,
        String userName,
        String userPassword,
        Boolean emailVerified,
        String memo,
        String profilePicture,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static UserAccountDto of(Long id, String email) {
        return new UserAccountDto(id, email, null, null, null, null, null, null, null);
    }

    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getId(),
                entity.getEmail(),
                entity.getUsername(),
                entity.getUserPassword(),
                entity.getEmailVerified(),
                entity.getMemo(),
                entity.getProfilePicture(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }
}
