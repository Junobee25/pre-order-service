package com.sideproject.preorderservice.dto.response;

import com.sideproject.preorderservice.dto.ArticleWithCommentDto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleWithCommentResponse(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        String email,
        Set<ArticleCommentResponse> articleCommentResponse

) {
    public static ArticleWithCommentResponse of(Long id, String title, String content, LocalDateTime createdAt, String email, Set<ArticleCommentResponse> articleCommentResponses) {
        return new ArticleWithCommentResponse(id, title, content, createdAt, email, articleCommentResponses);
    }

    public static ArticleWithCommentResponse fromArticleWithComment(ArticleWithCommentDto dto) {
        return new ArticleWithCommentResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                dto.articleCommentDto().stream()
                        .map(ArticleCommentResponse::fromArticleComment)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }
}
