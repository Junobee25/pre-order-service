package com.sideproject.preorderservice.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
public class EmailAuth {

    private static final Long MAX_EXPIRE_TIME = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column
    private String email;
    @Setter
    @Column
    private String authToken;
    @Setter
    @Column
    private Boolean expired;
    @Setter
    @Column
    private LocalDateTime expireDate;

    protected EmailAuth() {

    }

    @Builder
    private EmailAuth(String email, String authToken, Boolean expired) {
        this.email = email;
        this.authToken = authToken;
        this.expired = expired;
        this.expireDate = LocalDateTime.now().plusMinutes(MAX_EXPIRE_TIME);
    }

    // 빌더 패턴 사용
    public static EmailAuth of(String email, String authToken, Boolean expired) {
        return new EmailAuth(email, authToken, expired);
    }

    public void userToken() {
        this.expired = true;
    }

}
