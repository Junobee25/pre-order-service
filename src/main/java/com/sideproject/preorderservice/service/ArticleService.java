package com.sideproject.preorderservice.service;

import com.sideproject.preorderservice.configuration.AlarmType;
import com.sideproject.preorderservice.domain.AlarmArgs;
import com.sideproject.preorderservice.domain.Article;
import com.sideproject.preorderservice.domain.Follow;
import com.sideproject.preorderservice.domain.UserAccount;
import com.sideproject.preorderservice.domain.entity.AlarmEntity;
import com.sideproject.preorderservice.dto.ArticleDto;
import com.sideproject.preorderservice.dto.ArticleWithCommentDto;
import com.sideproject.preorderservice.exception.ErrorCode;
import com.sideproject.preorderservice.exception.PreOrderApplicationException;
import com.sideproject.preorderservice.repository.AlarmEntityRepository;
import com.sideproject.preorderservice.repository.ArticleRepository;
import com.sideproject.preorderservice.repository.FollowRepository;
import com.sideproject.preorderservice.repository.UserAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final UserAccountRepository userAccountRepository;
    private final ArticleRepository articleRepository;
    private final AlarmEntityRepository alarmEntityRepository;
    private final FollowRepository followRepository;

    @Transactional
    public void create(String email, String title, String content) {
        UserAccount userAccount = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("email is %s", email)));
        Article article = Article.of(userAccount, title, content);
        articleRepository.save(article);

        alarmEntityRepository.save(AlarmEntity.of(article.getUserAccount(), AlarmType.NEW_POST, new AlarmArgs(userAccount.getId(), article.getId())));
    }

    @Transactional
    public ArticleDto modify(String email, Long articleId, String title, String content) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.ARTICLE_NOT_FOUND, String.format("articleId is %d", articleId)));
        if (!Objects.equals(article.getUserAccount().getEmail(), email)) {
            throw new PreOrderApplicationException(ErrorCode.INVALID_PERMISSION, String.format("user %s has no permission with article %d", email, articleId));
        }
        article.setTitle(title);
        article.setContent(content);
        return ArticleDto.fromEntity(articleRepository.saveAndFlush(article));
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

    public Page<ArticleWithCommentDto> articleCheck(String email, Pageable pageable) {
        UserAccount userAccount = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("email is %s", email)));

        List<Follow> followedUser = followRepository.findByFromUserId(userAccount.getId());
        List<Long> followedUserIds = followedUser.stream()
                .map(Follow::getToUser)
                .map(UserAccount::getId)
                .collect(Collectors.toList());

        Page<Article> articles = articleRepository.findByUserAccount_IdIn(followedUserIds, pageable);
        return articles.map(ArticleWithCommentDto::from);
    }
}
