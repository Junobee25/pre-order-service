package com.sideproject.preorderservice.repository;

import com.sideproject.preorderservice.configuration.LikeType;
import com.sideproject.preorderservice.domain.Likes;
import com.sideproject.preorderservice.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserAccountAndLikeTypeAndTargetId(UserAccount userAccount, LikeType likeType, Long targetId);
}
