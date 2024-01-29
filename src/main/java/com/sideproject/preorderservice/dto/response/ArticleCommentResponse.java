package com.sideproject.preorderservice.dto.response;

import com.sideproject.preorderservice.domain.ArticleComment;

import java.time.LocalDateTime;

public record ArticleCommentResponse(
        Long id,
        String content,
        UserAccountResponse user,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {

    public static ArticleCommentResponse fromArticleComment(ArticleComment articleComment) {
        return new ArticleCommentResponse(
                articleComment.getId(),
                articleComment.getContent(),
                UserAccountResponse.fromUser(articleComment.getUserAccount()),
                articleComment.getCreatedAt(),
                articleComment.getModifiedAt()
        );
    }
}
