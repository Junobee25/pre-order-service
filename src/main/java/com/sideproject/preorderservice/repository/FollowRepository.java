package com.sideproject.preorderservice.repository;

import com.sideproject.preorderservice.domain.Follow;
import com.sideproject.preorderservice.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFromUserId(Long fromUserId);
}
