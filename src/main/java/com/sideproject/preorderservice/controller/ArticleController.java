package com.sideproject.preorderservice.controller;

import com.sideproject.preorderservice.dto.request.ArticleModifyRequest;
import com.sideproject.preorderservice.dto.request.ArticleWriteRequest;
import com.sideproject.preorderservice.dto.response.ArticleResponse;
import com.sideproject.preorderservice.dto.response.Response;
import com.sideproject.preorderservice.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/api/posts")
    public Response<Void> create(ArticleWriteRequest request, Authentication authentication) {
        articleService.create(authentication.getName(), request.title(), request.content());
        return Response.success();
    }

    @PutMapping("/api/posts/{articleId}")
    public Response<ArticleResponse> modify(@PathVariable Long articleId, ArticleModifyRequest request, Authentication authentication) {
        return Response.success(
                ArticleResponse.fromArticle(
                        articleService.modify(authentication.getName(), articleId, request.title(), request.content())
                )
        );
    }

    @DeleteMapping("/api/posts/{articleId}")
    public Response<Void> delete(@PathVariable Long articleId, Authentication authentication) {
        articleService.delete(authentication.getName(), articleId);
        return Response.success();
    }
}
