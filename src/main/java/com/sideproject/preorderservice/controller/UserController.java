package com.sideproject.preorderservice.controller;

import com.sideproject.preorderservice.dto.request.EmailAuthRequest;
import com.sideproject.preorderservice.dto.request.UserJoinRequest;
import com.sideproject.preorderservice.dto.request.UserLoginRequest;
import com.sideproject.preorderservice.dto.response.EmailAuthResponse;
import com.sideproject.preorderservice.dto.response.Response;
import com.sideproject.preorderservice.dto.response.UserJoinResponse;
import com.sideproject.preorderservice.dto.response.UserLoginResponse;
import com.sideproject.preorderservice.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
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

    @GetMapping("/join/confirm-email")
    public Response<EmailAuthResponse> confirmEmail(@ModelAttribute  EmailAuthRequest request) {
        userAccountService.confirmEmail(request.email(), request.authToken());
        return Response.success(new EmailAuthResponse(request.email()));
    }

    @PostMapping("/api/login")
    public Response<UserLoginResponse> login(UserLoginRequest request) {
        String token = userAccountService.login(request.email(), request.password());
        return Response.success(new UserLoginResponse(token));
    }

}
