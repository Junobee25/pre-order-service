package com.sideproject.preorderservice.service;

import com.sideproject.preorderservice.domain.Token;
import com.sideproject.preorderservice.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        Token storedAccessToken = tokenRepository.findByAccessToken(jwt).orElse(null);
        if (storedAccessToken != null) {
            storedAccessToken.setExpired(true);
            storedAccessToken.setRevoked(true);
            tokenRepository.save(storedAccessToken);
        }
    }
}
