package com.sideproject.preorderservice.controller;

import com.sideproject.preorderservice.dto.request.FollowRequest;
import com.sideproject.preorderservice.dto.response.Response;
import com.sideproject.preorderservice.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/api/follow")
    public Response<Void> follow(FollowRequest request, Authentication authentication) {
        //TODO: 팔로우 서비스 로직 구현하기
        followService.follow(authentication.getName(), request.toUser());
        return Response.success(null);
    }
}
