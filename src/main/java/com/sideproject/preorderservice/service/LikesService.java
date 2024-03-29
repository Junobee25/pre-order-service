package com.sideproject.preorderservice.service;

import com.sideproject.preorderservice.domain.constant.AlarmType;
import com.sideproject.preorderservice.domain.constant.LikeType;
import com.sideproject.preorderservice.domain.entity.*;
import com.sideproject.preorderservice.exception.ErrorCode;
import com.sideproject.preorderservice.exception.PreOrderApplicationException;
import com.sideproject.preorderservice.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final UserAccountRepository userAccountRepository;
    private final LikesRepository likesRepository;
    private final AlarmEntityRepository alarmEntityRepository;
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    @Transactional
    public void toggleLike(String email, LikeType likeType, Long targetId) {
        UserAccount userAccount = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("user is %s", email)));

        Optional<Likes> optionalLikes = likesRepository.findByUserAccountAndLikeTypeAndTargetId(userAccount, likeType, targetId);
        //TODO: 좋아요 타입이 더 많이 생길 경우 어떻게 처리 할 것인지 생각해 봐야 한다.
        if (likeType == LikeType.ARTICLE) {
            Article article = articleRepository.findById(targetId)
                    .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.ARTICLE_NOT_FOUND, String.format("articleId is %d", targetId)));
            articleLikeToggle(article, targetId, optionalLikes, userAccount);
        }

        if (likeType == LikeType.COMMENT) {
            ArticleComment articleComment = articleCommentRepository.findById(targetId)
                    .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.COMMENT_NOT_FOUND, String.format("commentId is %d", targetId)));
            commentLikeToggle(articleComment, targetId, optionalLikes, userAccount);
        }
    }

    private void articleLikeToggle(Article article, Long targetId, Optional<Likes> optionalLikes, UserAccount userAccount) {
        if (optionalLikes.isPresent()) {
            Likes likes = optionalLikes.get();
            likes.toggleDeleted();
            if (!likes.getDeleted()) {
                alarmEntityRepository.save(Alarm.of(article.getUserAccount(), userAccount.getId(), article.getId(), AlarmType.NEW_LIKE_ON_POST));
            }
            likesRepository.save(likes);
        }
        if (optionalLikes.isEmpty()) {
            Likes newLikes = Likes.of(userAccount, LikeType.ARTICLE, targetId);
            alarmEntityRepository.save(Alarm.of(article.getUserAccount(), userAccount.getId(), article.getId(), AlarmType.NEW_LIKE_ON_POST));
            likesRepository.save(newLikes);
        }
    }

    private void commentLikeToggle(ArticleComment articleComment, Long targetId, Optional<Likes> optionalLikes, UserAccount userAccount) {
        if (optionalLikes.isPresent()) {
            Likes likes = optionalLikes.get();
            likes.toggleDeleted();
            if (!likes.getDeleted()) {
                alarmEntityRepository.save(Alarm.of(articleComment.getUserAccount(), userAccount.getId(), articleComment.getId(), AlarmType.NEW_LIKE_ON_COMMENT));
            }
            likesRepository.save(likes);
        }
        if (optionalLikes.isEmpty()) {
            Likes newLikes = Likes.of(userAccount, LikeType.COMMENT, targetId);
            alarmEntityRepository.save(Alarm.of(articleComment.getUserAccount(), userAccount.getId(), articleComment.getId(), AlarmType.NEW_LIKE_ON_COMMENT));
            likesRepository.save(newLikes);
        }
    }
}

