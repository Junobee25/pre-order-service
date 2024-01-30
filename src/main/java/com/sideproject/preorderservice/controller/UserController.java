package com.sideproject.preorderservice.controller;

import com.sideproject.preorderservice.dto.request.EmailAuthRequest;
import com.sideproject.preorderservice.dto.request.UserJoinRequest;
import com.sideproject.preorderservice.dto.request.UserLoginRequest;
import com.sideproject.preorderservice.dto.response.*;
import com.sideproject.preorderservice.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/api/alarm")
    public Response<Page<AlarmResponse>> alarm(Pageable pageable, Authentication authentication) {
        return Response.success(userAccountService.alarmList(authentication.getName(), pageable).map(AlarmResponse::fromAlarm));
    }

}
