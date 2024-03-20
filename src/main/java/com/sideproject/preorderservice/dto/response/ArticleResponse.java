package com.sideproject.preorderservice.dto.response;

import com.sideproject.preorderservice.dto.ArticleDto;

public record ArticleResponse(
        Long id,
        String title,
        String content,
        UserAccountResponse user
) {
    public static ArticleResponse from(ArticleDto dto) {
        return new ArticleResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                UserAccountResponse.from(dto.userAccountDto())
        );
    }
}
