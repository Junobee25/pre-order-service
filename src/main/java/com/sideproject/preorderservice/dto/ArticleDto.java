package com.sideproject.preorderservice.dto;

import com.sideproject.preorderservice.domain.Article;

public record ArticleDto(
        Long id,
        String title,
        String content,
        UserAccountDto userAccountDto) {

    public static ArticleDto fromEntity(Article entity) {
        return new ArticleDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                UserAccountDto.from(entity.getUserAccount())
        );
    }
}
