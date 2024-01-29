package com.sideproject.preorderservice.dto.response;

import com.sideproject.preorderservice.domain.Article;

import java.time.LocalDateTime;

public record ArticleResponse(
        Long id,
        String title,
        String content,
        UserAccountResponse user,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static ArticleResponse fromArticle(Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                UserAccountResponse.fromUser(article.getUserAccount()),
                article.getCreatedAt(),
                article.getModifiedAt()
        );
    }
}
