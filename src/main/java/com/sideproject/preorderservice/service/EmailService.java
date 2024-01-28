package com.sideproject.preorderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Async
    public void send(String email, String authToken) {
        SimpleMailMessage smm = new SimpleMailMessage();
        //TODO: 이메일 정보는 노출할 수 밖에 없으므로 ID를 이메일을 아닌 형식으로 변경해야 겠다. 식별정보는 id로 하고 Token을 UUID로 바꿔 민감정보를 감춘다.
        smm.setTo(email);
        smm.setSubject("회원가입 이메일 인증");
        smm.setText("http://localhost:8080/join/confirm-email?email="+email+"&authToken="+authToken);

        javaMailSender.send(smm);
    }
}
