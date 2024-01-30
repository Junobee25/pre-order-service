package com.sideproject.preorderservice.dto.response;

import com.sideproject.preorderservice.dto.UserAccountDto;

public record UserAccountResponse(
        Long id,
        String email) {

    public static UserAccountResponse fromUser(UserAccountDto dto) {
        return new UserAccountResponse(
                dto.id(),
                dto.email()
        );
    }
}
