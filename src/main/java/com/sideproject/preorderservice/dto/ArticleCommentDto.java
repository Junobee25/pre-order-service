package com.sideproject.preorderservice.dto;

import com.sideproject.preorderservice.domain.Article;
import com.sideproject.preorderservice.domain.ArticleComment;
import com.sideproject.preorderservice.domain.UserAccount;

import java.time.LocalDateTime;

public record ArticleCommentDto(
        Long id,
        Long articleId,
        UserAccountDto userAccountDto,
        String content,

        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {

    public static ArticleCommentDto of(Long id, Long articleId, UserAccountDto userAccountDto, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new ArticleCommentDto(id, articleId, userAccountDto, content, createdAt, modifiedAt);
    }

    public static ArticleCommentDto from(ArticleComment entity) {
        return new ArticleCommentDto(
                entity.getId(),
                entity.getArticle().getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

    public ArticleComment toEntity(Article article, UserAccount userAccount) {
        return ArticleComment.of(
                userAccount,
                article,
                content
        );
    }
}
