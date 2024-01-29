package com.sideproject.preorderservice.service;

import com.sideproject.preorderservice.domain.Article;
import com.sideproject.preorderservice.domain.UserAccount;
import com.sideproject.preorderservice.exception.ErrorCode;
import com.sideproject.preorderservice.exception.PreOrderApplicationException;
import com.sideproject.preorderservice.repository.ArticleRepository;
import com.sideproject.preorderservice.repository.UserAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final UserAccountRepository userAccountRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public void create(String email, String title, String content) {
        UserAccount userAccount = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("email is %s", email)));
        Article article = Article.of(userAccount, title, content);
        articleRepository.save(article);
    }

    @Transactional
    public Article modify(String email, Long articleId, String title, String content) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.ARTICLE_NOT_FOUND, String.format("articleId is %d", articleId)));
        if (!Objects.equals(article.getUserAccount().getEmail(), email)) {
            throw new PreOrderApplicationException(ErrorCode.INVALID_PERMISSION, String.format("user %s has no permission with article %d", email, articleId));
        }
        article.setTitle(title);
        article.setContent(content);
        return articleRepository.saveAndFlush(article);
    }

    @Transactional
    public void delete(String email, Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.ARTICLE_NOT_FOUND, String.format("articleId is %d", articleId)));
        if (!Objects.equals(article.getUserAccount().getEmail(), email)) {
            throw new PreOrderApplicationException(ErrorCode.INVALID_PERMISSION, String.format("user %s has no permission with article %d", email, articleId));
        }
        articleRepository.delete(article);
    }
}
