package com.sideproject.preorderservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sideproject.preorderservice.domain.constant.TokenType;
import com.sideproject.preorderservice.domain.entity.EmailAuth;
import com.sideproject.preorderservice.domain.entity.Follow;
import com.sideproject.preorderservice.domain.entity.Token;
import com.sideproject.preorderservice.domain.entity.UserAccount;
import com.sideproject.preorderservice.dto.AlarmDto;
import com.sideproject.preorderservice.dto.UserAccountDto;
import com.sideproject.preorderservice.dto.response.UserLoginResponse;
import com.sideproject.preorderservice.exception.ErrorCode;
import com.sideproject.preorderservice.exception.PreOrderApplicationException;
import com.sideproject.preorderservice.repository.*;
import com.sideproject.preorderservice.util.JwtTokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final EmailAuthRepository emailAuthRepository;
    private final AlarmEntityRepository alarmEntityRepository;
    private final FollowRepository followRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder encoder;
    private final EmailService emailService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    @Value("${jwt.refreshToken.expired-time-ms}")
    private Long expiredRefreshTokenTimeMs;

    public UserAccount loadUserByEmail(String email) throws PreOrderApplicationException {
        return userAccountRepository.findByEmail(email).orElseThrow(
                () -> new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("email is %s", email))
        );
    }

    public UserAccountDto join(String email, String userName, String userPassword, String memo) {
        //TODO: 생성자에 값을 즉시 주입시켜 생성할 수 있는 Build 패턴이 유용한 것 같다 회원가입은 dto를 통해 값을 전달 했는데 Build 패턴으로 리팩토링해보자
        EmailAuth emailAuth = emailAuthRepository.save(
                EmailAuth.builder()
                        .email(email)
                        .authToken(UUID.randomUUID().toString())
                        .expired(false)
                        .build());

        userAccountRepository.findByEmail(email).ifPresent(it -> {
            throw new PreOrderApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", email));
        });

        UserAccount savedUser = userAccountRepository.save(UserAccount.of(email, userName, encoder.encode(userPassword), false, memo));
        emailService.send(emailAuth.getEmail(), emailAuth.getAuthToken());
        return UserAccountDto.from(savedUser);
    }

    public UserLoginResponse login(String email, String password) {
        UserAccount userAccount = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", email)));

        if (!encoder.matches(password, userAccount.getUserPassword())) {
            throw new PreOrderApplicationException(ErrorCode.INVALID_PASSWORD);
        }
        if (!userAccount.getEmailVerified()) {
            throw new PreOrderApplicationException(ErrorCode.USER_NOT_AUTHENTICATED);
        }

        String accessToken = JwtTokenUtils.generateToken(email, secretKey, expiredTimeMs);
        String refreshToken = JwtTokenUtils.generateRefreshToken(email, secretKey, expiredRefreshTokenTimeMs);
        revokeAllUserTokens(email);
        saveToken(email, accessToken);
        return new UserLoginResponse(accessToken, refreshToken);
    }

    @Transactional
    public void modifyProfile(String email, String userName, String memo) {
        UserAccount userAccount = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", email)));

        userAccount.setUserName(userName);
        userAccount.setMemo(memo);
        userAccountRepository.save(userAccount);
    }

    @Transactional
    public UserLoginResponse modifyPassword(String email, String currentPassword, String newPassword) {
        UserAccount userAccount = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", email)));

        if (!encoder.matches(currentPassword, userAccount.getUserPassword())) {
            throw new PreOrderApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        userAccount.setUserPassword(encoder.encode(newPassword));
        userAccountRepository.save(userAccount);

        String accessToken = JwtTokenUtils.generateToken(email, secretKey, expiredTimeMs);
        String refreshToken = JwtTokenUtils.generateRefreshToken(email, secretKey, expiredRefreshTokenTimeMs);
        revokeAllUserTokens(email);
        saveToken(email, accessToken);
        return new UserLoginResponse(accessToken, refreshToken);
    }


    private void revokeAllUserTokens(String email) {
        List<Token> validTokens = tokenRepository.findAllValidTokenByEmail(email);
        if (!validTokens.isEmpty()) {
            validTokens.forEach(t -> {
                t.setExpired(true);
                t.setRevoked(true);
            });
        }
    }

    private void saveToken(String email, String accessToken) {
        Token token = Token.builder()
                .accessToken(accessToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .email(email)
                .build();
        tokenRepository.save(token);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String email;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        email = JwtTokenUtils.extractEmail(refreshToken, secretKey);
        if (email != null) {
            UserAccount userAccount = userAccountRepository.findByEmail(email)
                    .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("email is %s", email)));
            if (JwtTokenUtils.isTokenValid(refreshToken, secretKey, userAccount)) {
                String accessToken = JwtTokenUtils.generateToken(userAccount.getEmail(), secretKey, expiredTimeMs);
                UserLoginResponse userLoginResponse = new UserLoginResponse(accessToken, refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), userLoginResponse);
            }
        }
    }


    @Transactional // JPA에서 영속성 컨텍스트 통해 객체 변경 추적하고 트랜잭션 커밋 시점에 DB에 반영해줌.
    public void confirmEmail(String email, String authToken) {
        EmailAuth emailAuth = emailAuthRepository.findValidAuthByEmail(email, authToken, LocalDateTime.now())
                .orElseThrow(PreOrderApplicationException::new);
        UserAccount userAccount = userAccountRepository.findByEmail(email)
                .orElseThrow(PreOrderApplicationException::new);
        emailAuth.userToken();
        //TODO: Setter로 대체 됨 뭘 사용할지에 대한 기준은 잘 모르겠음
        userAccount.emailVerifiedSuccess();
    }

    @Transactional(readOnly = true)
    public Page<AlarmDto> alarmList(String email, Pageable pageable) {
        UserAccount userAccount = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", email)));
        List<Long> followedUserIds = followRepository.findByFromUserId(userAccount.getId()).stream()
                .map(Follow::getToUser)
                .map(UserAccount::getId)
                .toList();

        return alarmEntityRepository.findAllByFromUserIdIn(followedUserIds, pageable)
                .map(AlarmDto::fromEntity);
    }
}
