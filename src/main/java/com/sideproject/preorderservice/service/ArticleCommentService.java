package com.sideproject.preorderservice.service;

import com.sideproject.preorderservice.domain.Article;
import com.sideproject.preorderservice.domain.ArticleComment;
import com.sideproject.preorderservice.domain.UserAccount;
import com.sideproject.preorderservice.exception.ErrorCode;
import com.sideproject.preorderservice.exception.PreOrderApplicationException;
import com.sideproject.preorderservice.repository.ArticleCommentRepository;
import com.sideproject.preorderservice.repository.ArticleRepository;
import com.sideproject.preorderservice.repository.UserAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ArticleCommentService {
    private final UserAccountRepository userAccountRepository;
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    @Transactional
    public void create(String email, Long articleId, String content) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.ARTICLE_NOT_FOUND, String.format("articleId is %d", articleId)));
        UserAccount userAccount = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("user is %s", email)));

        articleCommentRepository.save(ArticleComment.of(userAccount, article, content));
    }

    @Transactional
    public ArticleComment modify(String email, Long commentId, String content) {
        ArticleComment articleComment = articleCommentRepository.findById(commentId)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.COMMENT_NOT_FOUND, String.format("commentId is %d", commentId)));
        if (!Objects.equals(articleComment.getUserAccount().getEmail(), email)) {
            throw new PreOrderApplicationException(ErrorCode.INVALID_PERMISSION, String.format("user %s has no permission with articleComment %d", email, commentId));
        }
        articleComment.setContent(content);

        return articleCommentRepository.save(articleComment);
    }

    @Transactional
    public void delete(String email, Long commentId) {
        ArticleComment articleComment = articleCommentRepository.findById(commentId)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.COMMENT_NOT_FOUND, String.format("commentId is %d", commentId)));
        if (!Objects.equals(articleComment.getUserAccount().getEmail(), email)) {
            throw new PreOrderApplicationException(ErrorCode.INVALID_PERMISSION, String.format("user %s has no permission with articleComment %d", email, commentId));
        }
        articleCommentRepository.delete(articleComment);
    }
}
