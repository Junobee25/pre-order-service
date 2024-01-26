package com.sideproject.preorderservice.repository;

import com.sideproject.preorderservice.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
