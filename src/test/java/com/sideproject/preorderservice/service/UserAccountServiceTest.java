package com.sideproject.preorderservice.service;

import com.sideproject.preorderservice.domain.entity.EmailAuth;
import com.sideproject.preorderservice.domain.entity.UserAccount;
import com.sideproject.preorderservice.dto.UserAccountDto;
import com.sideproject.preorderservice.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

    @InjectMocks
    private UserAccountService sut;
    @Mock
    private UserAccountRepository userAccountRepository;
    @Mock
    private EmailAuthRepository emailAuthRepository;
    @Mock
    private AlarmEntityRepository alarmEntityRepository;
    @Mock
    private FollowRepository followRepository;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private BCryptPasswordEncoder encoder;
    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(sut, "secretKey", "test1234567891011121314151617181920212223");
        ReflectionTestUtils.setField(sut, "expiredTimeMs", 1L);
        ReflectionTestUtils.setField(sut, "expiredRefreshTokenTimeMs", 1L);
    }

    @DisplayName("[POST] 회원 정보를 입력하면, 새로운 회원 정보를 저장하여 가입시키고 해당 회원 데이터를 리턴한다.")
    @Test
    void join() {
        // given
        UserAccount userAccount = createUserAccount();
        given(emailAuthRepository.save(any(EmailAuth.class))).willReturn(createEmailAuth());
        given(userAccountRepository.save(userAccount)).willReturn(userAccount);

        //when
        UserAccountDto result = sut.join(
                userAccount.getEmail(),
                userAccount.getUsername(),
                userAccount.getPassword(),
                userAccount.getMemo()
        );

        //then
        assertThat(result)
                .hasFieldOrPropertyWithValue("email", userAccount.getEmail())
                .hasFieldOrPropertyWithValue("userName", userAccount.getUsername())
                .hasFieldOrPropertyWithValue("userPassword", userAccount.getUserPassword())
                .hasFieldOrPropertyWithValue("memo", userAccount.getMemo());
        then(userAccountRepository).should().save(userAccount);
    }

    @DisplayName("[POST] 로그인 정상 동작")
    @Test
    void login() {
        //given
        UserAccount userAccount = createUserAccount();

        //when
        when(userAccountRepository.findByEmail(userAccount.getEmail())).thenReturn(Optional.of(userAccount));
        when(encoder.matches(userAccount.getUserPassword(), "password_encrypt")).thenReturn(true);

        //then
        Assertions.assertDoesNotThrow(() -> sut.login(userAccount.getEmail(), userAccount.getUserPassword()));
    }


    private EmailAuth createEmailAuth() {
        return EmailAuth.of("test", "test", false);
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "test",
                "test",
                "password_encrypt",
                true,
                "memo"
        );
    }
}
