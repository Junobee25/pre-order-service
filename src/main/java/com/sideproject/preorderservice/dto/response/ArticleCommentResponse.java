package com.sideproject.preorderservice.dto.response;

import com.sideproject.preorderservice.dto.ArticleCommentDto;

import java.time.LocalDateTime;

public record ArticleCommentResponse(
        Long id,
        String content,
        UserAccountResponse user,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static ArticleCommentResponse from(ArticleCommentDto dto) {
        return new ArticleCommentResponse(
                dto.id(),
                dto.content(),
                UserAccountResponse.from(dto.userAccountDto()),
                dto.createdAt(),
                dto.modifiedAt()
        );
    }
}
