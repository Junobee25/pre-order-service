package com.sideproject.preorderservice.controller;


import com.sideproject.preorderservice.dto.request.ArticleCommentRequest;
import com.sideproject.preorderservice.dto.request.ArticleModifyRequest;
import com.sideproject.preorderservice.dto.response.ArticleCommentResponse;
import com.sideproject.preorderservice.dto.response.Response;
import com.sideproject.preorderservice.service.ArticleCommentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/api/posts/{articleId}/comments")
    public Response<Void> create(@PathVariable Long articleId, ArticleCommentRequest request, Authentication authentication) {
        articleCommentService.create(authentication.getName(), articleId, request.content());
        return Response.success();
    }

    @PutMapping("/api/posts/comments/{commentId}")
    public Response<ArticleCommentResponse> modify(@PathVariable Long commentId, ArticleModifyRequest request, Authentication authentication) {
        return Response.success(
                ArticleCommentResponse.fromArticleComment(
                        articleCommentService.modify(authentication.getName(), commentId, request.content())
                )
        );
    }

    @DeleteMapping("/api/posts/comments/{commentId}")
    public Response<Void> delete(@PathVariable Long commentId, Authentication authentication) {
        articleCommentService.delete(authentication.getName(), commentId);
        return Response.success();
    }
}
