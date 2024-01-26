package com.sideproject.preorderservice.repository;

import com.sideproject.preorderservice.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
