package com.sideproject.preorderservice.repository;

import com.sideproject.preorderservice.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
