package com.sideproject.preorderservice.repository;

import com.sideproject.preorderservice.domain.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long>, EmailAuthCustomRepository {
}
