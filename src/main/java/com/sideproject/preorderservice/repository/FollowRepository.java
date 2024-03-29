package com.sideproject.preorderservice.repository;

import com.sideproject.preorderservice.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFromUserId(Long fromUserId);
    Optional<Follow> findByFromUserIdAndToUserId(Long fromUserId, Long ToUserId);
}
