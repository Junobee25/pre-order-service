package com.sideproject.preorderservice.dto;

import com.sideproject.preorderservice.domain.entity.Article;

public record ArticleDto(
        Long id,
        String title,
        String content,
        UserAccountDto userAccountDto
) {
    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                UserAccountDto.from(entity.getUserAccount())
        );
    }
}
