package com.sideproject.preorderservice.repository;

import com.sideproject.preorderservice.domain.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
}
