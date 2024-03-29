package com.sideproject.preorderservice.controller;

import com.sideproject.preorderservice.dto.request.EmailAuthRequest;
import com.sideproject.preorderservice.dto.request.UserJoinRequest;
import com.sideproject.preorderservice.dto.request.UserLoginRequest;
import com.sideproject.preorderservice.dto.request.UserProfileModifyRequest;
import com.sideproject.preorderservice.dto.response.*;
import com.sideproject.preorderservice.service.UserAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/api/join")
    public Response<UserJoinResponse> join(UserJoinRequest request) {
        return Response.success(UserJoinResponse.from(userAccountService.join(
                request.email(),
                request.userName(),
                request.password(),
                request.memo())));
    }

    @GetMapping("/join/confirm-email")
    public Response<EmailAuthResponse> confirmEmail(@ModelAttribute EmailAuthRequest request) {
        userAccountService.confirmEmail(request.email(), request.authToken());
        return Response.success(new EmailAuthResponse(request.email()));
    }

    @PostMapping("/api/login")
    public Response<UserLoginResponse> login(UserLoginRequest request) {
        return Response.success(userAccountService.login(request.email(), request.password()));
    }

    @GetMapping("/api/alarm")
    public Response<Page<AlarmResponse>> alarm(Pageable pageable, Authentication authentication) {
        return Response.success(userAccountService.alarmList(authentication.getName(), pageable).map(AlarmResponse::from));
    }


    // 인증 상태를 유지하기 위한 refresh 토큰 (만료시간이 가까워지면 프론트 측에서 해당 api를 호출하여 로그인 상태가 유지 될 수 있게 해야함)
    @PostMapping("/api/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userAccountService.refreshToken(request, response);
    }

    @PutMapping("/api/modify-profile")
    public void modifyProfile(UserProfileModifyRequest request, Authentication authentication) {
        //TODO: 프로필 변경시 프로필 사진은 일단 String 으로 대체한다. 후에 변경
        userAccountService.modifyProfile(authentication.getName(), request.userName(), request.memo());
    }

    @PutMapping("/api/modify-password")
    public Response<UserLoginResponse> modifyPassword(String currentPassword, String newPassword, Authentication authentication) {
        return Response.success(userAccountService.modifyPassword(authentication.getName(), currentPassword, newPassword));
    }
}
