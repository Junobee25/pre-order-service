package com.sideproject.preorderservice.service;

import com.sideproject.preorderservice.configuration.AlarmType;
import com.sideproject.preorderservice.configuration.LikeType;
import com.sideproject.preorderservice.domain.*;
import com.sideproject.preorderservice.domain.entity.AlarmEntity;
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

        if (likeType == LikeType.ARTICLE) {
            Article article = articleRepository.findById(targetId)
                    .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.ARTICLE_NOT_FOUND, String.format("articleId is %d", targetId)));

            if (optionalLikes.isPresent()) {
                Likes likes = optionalLikes.get();
                likes.toggleDeleted();
                if (!likes.getDeleted()) {
                    alarmEntityRepository.save(AlarmEntity.of(article.getUserAccount(), AlarmType.NEW_LIKE_ON_POST, new AlarmArgs(userAccount.getId(), article.getId())));
                }
                likesRepository.save(likes);
            } else {
                Likes newLikes = Likes.of(userAccount, likeType, targetId);
                alarmEntityRepository.save(AlarmEntity.of(article.getUserAccount(), AlarmType.NEW_LIKE_ON_POST, new AlarmArgs(userAccount.getId(), article.getId())));
                likesRepository.save(newLikes);
            }
        }

        if (likeType == LikeType.COMMENT) {
            ArticleComment articleComment = articleCommentRepository.findById(targetId)
                    .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.COMMENT_NOT_FOUND, String.format("commentId is %d", targetId)));

            if (optionalLikes.isPresent()) {
                Likes likes = optionalLikes.get();
                likes.toggleDeleted();
                if (!likes.getDeleted()) {
                    alarmEntityRepository.save(AlarmEntity.of(articleComment.getUserAccount(), AlarmType.NEW_LIKE_ON_COMMENT, new AlarmArgs(userAccount.getId(), articleComment.getId())));
                }
                likesRepository.save(likes);
            } else {
                Likes newLikes = Likes.of(userAccount, likeType, targetId);
                alarmEntityRepository.save(AlarmEntity.of(articleComment.getUserAccount(), AlarmType.NEW_LIKE_ON_COMMENT, new AlarmArgs(userAccount.getId(), articleComment.getId())));
                likesRepository.save(newLikes);
            }
        }
    }
}
