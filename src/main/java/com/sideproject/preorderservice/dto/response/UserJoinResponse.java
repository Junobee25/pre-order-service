package com.sideproject.preorderservice.dto.response;

import com.sideproject.preorderservice.dto.UserAccountDto;

public record UserJoinResponse(
        Long id,
        String email
) {

    public static UserJoinResponse of(Long id, String email) {
        return new UserJoinResponse(id, email);
    }
    public static UserJoinResponse from(UserAccountDto userAccountDto) {
        return new UserJoinResponse(
                userAccountDto.id(),
                userAccountDto.email()
        );
    }
}
