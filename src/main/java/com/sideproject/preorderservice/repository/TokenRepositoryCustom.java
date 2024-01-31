package com.sideproject.preorderservice.repository;

import com.sideproject.preorderservice.domain.Token;

import java.util.List;

public interface TokenRepositoryCustom {
    List<Token> findAllValidTokenByEmail(String email);
}
