package com.sideproject.preorderservice.configuration;

import com.sideproject.preorderservice.configuration.filter.JwtTokenFilter;
import com.sideproject.preorderservice.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfiguration {
    private final UserAccountService userAccountService;
    @Value("${jwt.secret-key}")
    private String secretKey;
    private final LogoutHandler logoutService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/join",
                                "/api/login",
                                "/join/confirm-email/**",
                                "/api/follow",
                                "/api/posts/**",
                                "/api/likes/**",
                                "/api/alarm/**"
                        ).permitAll()
                )
                .addFilterBefore(new JwtTokenFilter(userAccountService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .logout(logoutConfig -> {
                    logoutConfig.logoutUrl("/api/logout")
                            .addLogoutHandler(logoutService)
                            .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
                });

        return http.build();
    }

}
