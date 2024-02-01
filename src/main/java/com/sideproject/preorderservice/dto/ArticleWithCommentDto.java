package com.sideproject.preorderservice.dto;

import com.sideproject.preorderservice.domain.entity.Article;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleWithCommentDto(
        Long id,
        String title,
        String content,
        UserAccountDto userAccountDto,
        Set<ArticleCommentDto> articleCommentDto,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt

) {
    public static ArticleWithCommentDto from(Article article) {
        return new ArticleWithCommentDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                UserAccountDto.from(article.getUserAccount()),
                article.getArticleComments().stream()
                        .map(ArticleCommentDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                article.getCreatedAt(),
                article.getModifiedAt()
        );
    }
}
