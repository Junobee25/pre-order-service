package com.sideproject.preorderservice.dto.response;

import com.sideproject.preorderservice.dto.ArticleDto;

import java.time.LocalDateTime;

public record ArticleResponse(
        Long id,
        String title,
        String content,
        UserAccountResponse user,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static ArticleResponse from(ArticleDto dto) {
        return new ArticleResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                UserAccountResponse.from(dto.userAccountDto()),
                dto.userAccountDto().createdAt(),
                dto.userAccountDto().modifiedAt()
        );
    }
}
