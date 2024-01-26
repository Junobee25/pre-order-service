package com.sideproject.preorderservice.service;

import com.sideproject.preorderservice.domain.UserAccount;
import com.sideproject.preorderservice.dto.UserAccountDto;
import com.sideproject.preorderservice.exception.ErrorCode;
import com.sideproject.preorderservice.exception.PreOrderApplicationException;
import com.sideproject.preorderservice.repository.UserAccountRepository;
import com.sideproject.preorderservice.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    public UserAccountDto join(String email, String userName, String userPassword, String memo, MultipartFile profilePicture) {
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
        return UserAccountDto.from(savedUser);
    }

    public String login(String email, String password) {
        UserAccount userAccount = userAccountRepository.findByEmail(email).orElseThrow(() -> new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("% not founded", email)));

        if (!encoder.matches(password, userAccount.getUserPassword())) {
            throw new PreOrderApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        String token = JwtTokenUtils.generateToken(email, secretKey, expiredTimeMs);

        return token;
    }
}
