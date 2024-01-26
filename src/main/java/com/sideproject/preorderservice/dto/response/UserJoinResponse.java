package com.sideproject.preorderservice.dto.response;

import com.sideproject.preorderservice.dto.UserAccountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;


public record UserJoinResponse(
        Long id,
        String email
) {

    public static UserJoinResponse from(UserAccountDto userAccountDto) {
        return new UserJoinResponse(
                userAccountDto.id(),
                userAccountDto.email()
        );
    }
}
