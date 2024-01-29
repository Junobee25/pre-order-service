package com.sideproject.preorderservice.dto.request;

public record ArticleWriteRequest(
        String title,
        String content
) {
}
