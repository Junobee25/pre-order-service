package com.sideproject.preorderservice.controller;


import com.sideproject.preorderservice.dto.request.ArticleCommentModifyRequest;
import com.sideproject.preorderservice.dto.request.ArticleCommentRequest;
import com.sideproject.preorderservice.dto.response.ArticleCommentModifyResponse;
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
    public Response<ArticleCommentModifyResponse> modify(@PathVariable Long commentId, ArticleCommentModifyRequest request, Authentication authentication) {
        return Response.success(
                ArticleCommentModifyResponse.from(
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
