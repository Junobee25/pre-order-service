package com.sideproject.preorderservice.dto.response;

import com.sideproject.preorderservice.dto.ArticleDto;

import java.time.LocalDateTime;

public record ArticleModifyResponse(
        Long id,
        String title,
        String content,
        UserAccountResponse user
) {
    public static ArticleModifyResponse from(ArticleDto dto) {
        return new ArticleModifyResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                UserAccountResponse.from(dto.userAccountDto())
        );
    }
}
