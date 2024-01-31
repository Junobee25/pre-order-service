package com.sideproject.preorderservice.service;

import com.sideproject.preorderservice.domain.EmailAuth;
import com.sideproject.preorderservice.domain.Follow;
import com.sideproject.preorderservice.domain.UserAccount;
import com.sideproject.preorderservice.dto.AlarmDto;
import com.sideproject.preorderservice.dto.UserAccountDto;
import com.sideproject.preorderservice.exception.ErrorCode;
import com.sideproject.preorderservice.exception.PreOrderApplicationException;
import com.sideproject.preorderservice.repository.AlarmEntityRepository;
import com.sideproject.preorderservice.repository.EmailAuthRepository;
import com.sideproject.preorderservice.repository.FollowRepository;
import com.sideproject.preorderservice.repository.UserAccountRepository;
import com.sideproject.preorderservice.util.JwtTokenUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final EmailAuthRepository emailAuthRepository;
    private final AlarmEntityRepository alarmEntityRepository;
    private final FollowRepository followRepository;
    private final BCryptPasswordEncoder encoder;
    private final EmailService emailService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    public UserAccount loadUserByEmail(String email) throws PreOrderApplicationException {
        return userAccountRepository.findByEmail(email).orElseThrow(
                () -> new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("email is %s", email))
        );
    }

    public UserAccountDto join(String email, String userName, String userPassword, String memo, MultipartFile profilePicture) {
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

        String profilePictureBase64 = null;
        if (profilePicture != null && !profilePicture.isEmpty()) {
            try {
                byte[] bytes = profilePicture.getBytes();
                profilePictureBase64 = Base64.getEncoder().encodeToString(bytes);
            } catch (IOException e) {
                //TODO: Logger
                e.printStackTrace();
            }
        }
        profilePictureBase64 = "/temp/img";
        UserAccount savedUser = userAccountRepository.save(UserAccount.of(email, userName, encoder.encode(userPassword), false, memo, profilePictureBase64));
        emailService.send(emailAuth.getEmail(), emailAuth.getAuthToken());
        return UserAccountDto.from(savedUser);
    }

    public String login(String email, String password) {
        UserAccount userAccount = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("% not founded", email)));

        if (!encoder.matches(password, userAccount.getUserPassword())) {
            throw new PreOrderApplicationException(ErrorCode.INVALID_PASSWORD);
        }
        if (!userAccount.getEmailVerified()) {
            throw new PreOrderApplicationException(ErrorCode.USER_NOT_AUTHENTICATED);
        }

        String token = JwtTokenUtils.generateToken(email, secretKey, expiredTimeMs);

        return token;
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

    public Page<AlarmDto> alarmList(String email, Pageable pageable) {
        UserAccount userAccount = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("% not founded", email)));
        List<Follow> followedUser = followRepository.findByFromUserId(userAccount.getId());
        List<Long> followedUserIds = followedUser.stream()
                .map(Follow::getToUser)
                .map(UserAccount::getId)
                .toList();

        return alarmEntityRepository.findAllByFromUserIdIn(followedUserIds, pageable)
                .map(AlarmDto::fromEntity);
    }
}
