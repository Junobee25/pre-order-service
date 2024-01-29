package com.sideproject.preorderservice.dto.request;

import com.sideproject.preorderservice.domain.UserAccount;

public record FollowRequest(
        String toUser
) {
}
