package com.sideproject.preorderservice.controller;

import com.sideproject.preorderservice.dto.request.UserJoinRequest;
import com.sideproject.preorderservice.dto.request.UserLoginRequest;
import com.sideproject.preorderservice.dto.response.Response;
import com.sideproject.preorderservice.dto.response.UserJoinResponse;
import com.sideproject.preorderservice.dto.response.UserLoginResponse;
import com.sideproject.preorderservice.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserAccountService userAccountService;

    @PostMapping("/api/join")
    public Response<UserJoinResponse> join(UserJoinRequest request) {
        return Response.success(UserJoinResponse.from(userAccountService.join(
                request.email(),
                request.userName(),
                request.password(),
                request.memo(),
                request.profilePicture())));
    }

    @PostMapping("/api/login")
    public Response<UserLoginResponse> login(UserLoginRequest request) {
        String token = userAccountService.login(request.email(), request.password());
        return Response.success(new UserLoginResponse(token));
    }

}
