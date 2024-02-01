package com.sideproject.preorderservice.repository;
import jakarta.persistence.EntityManager;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideproject.preorderservice.domain.entity.QToken;
import com.sideproject.preorderservice.domain.entity.Token;

import java.util.List;

public class TokenRepositoryCustomImpl implements TokenRepositoryCustom {


    JPAQueryFactory jpaQueryFactory;
    public TokenRepositoryCustomImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }
    @Override
    public List<Token> findAllValidTokenByEmail(String email) {
        return jpaQueryFactory
                .selectFrom(QToken.token)
                .where(QToken.token.email.eq(email))
                .fetch();
    }
}