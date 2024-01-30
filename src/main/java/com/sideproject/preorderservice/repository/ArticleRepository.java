package com.sideproject.preorderservice.repository;

import com.sideproject.preorderservice.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByUserAccount_IdIn(List<Long> userIds, Pageable pageable);

}
