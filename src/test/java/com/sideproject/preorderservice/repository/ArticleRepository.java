package com.sideproject.preorderservice.repository;

import com.sideproject.preorderservice.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
