package com.sideproject.preorderservice.controller;

import com.sideproject.preorderservice.configuration.LikeType;
import com.sideproject.preorderservice.dto.response.Response;
import com.sideproject.preorderservice.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

    @PostMapping("/api/likes/{likeType}/{targetId}")
    public Response<Void> toggleLike(@PathVariable LikeType likeType, @PathVariable Long targetId, Authentication authentication) {
        likesService.toggleLike(authentication.getName(), likeType, targetId);
        return Response.success();
    }
}
