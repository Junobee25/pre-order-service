package com.sideproject.preorderservice.dto.response;

import com.sideproject.preorderservice.dto.ArticleCommentDto;

import java.time.LocalDateTime;

public record ArticleCommentModifyResponse(
        Long id,
        String content,
        UserAccountResponse user,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static ArticleCommentModifyResponse from(ArticleCommentDto dto) {
        return new ArticleCommentModifyResponse(
                dto.id(),
                dto.content(),
                UserAccountResponse.from(dto.userAccountDto()),
                dto.createdAt(),
                dto.modifiedAt()
        );
    }
}
