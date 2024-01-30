package com.sideproject.preorderservice.configuration;

import com.sideproject.preorderservice.configuration.filter.JwtTokenFilter;
import com.sideproject.preorderservice.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfiguration {
    private final UserAccountService userAccountService;
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/join",
                                "/api/login",
                                "/join/confirm-email/**",
                                "/api/follow",
                                "/api/posts/**",
                                "/api/likes/**"
                        ).permitAll()
                )
                .addFilterBefore(new JwtTokenFilter(userAccountService, secretKey), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
