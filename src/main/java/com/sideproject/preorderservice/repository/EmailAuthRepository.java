package com.sideproject.preorderservice.repository;

import com.sideproject.preorderservice.domain.entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long>, EmailAuthCustomRepository {
}
