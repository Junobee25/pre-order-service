package com.sideproject.preorderservice.repository;

import com.sideproject.preorderservice.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long>, TokenRepositoryCustom{
    Optional<Token> findByAccessToken(String token);
}
