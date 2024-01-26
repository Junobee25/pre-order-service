package com.sideproject.preorderservice.repository;

import com.sideproject.preorderservice.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}
