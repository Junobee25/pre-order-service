package com.sideproject.preorderservice.dto.response;

import com.sideproject.preorderservice.domain.UserAccount;

public record UserAccountResponse(
        Long id,
        String email) {

    public static UserAccountResponse fromUser(UserAccount userAccount) {
        return new UserAccountResponse(
                userAccount.getId(),
                userAccount.getEmail()
        );
    }
}
