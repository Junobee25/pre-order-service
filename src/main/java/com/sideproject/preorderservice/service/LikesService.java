package com.sideproject.preorderservice.service;

import com.sideproject.preorderservice.configuration.LikeType;
import com.sideproject.preorderservice.domain.Likes;
import com.sideproject.preorderservice.domain.UserAccount;
import com.sideproject.preorderservice.exception.ErrorCode;
import com.sideproject.preorderservice.exception.PreOrderApplicationException;
import com.sideproject.preorderservice.repository.LikesRepository;
import com.sideproject.preorderservice.repository.UserAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final UserAccountRepository userAccountRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public void toggleLike(String email, LikeType likeType, Long targetId) {
        UserAccount userAccount = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("user is %s", email)));
        Likes likes = likesRepository.findByUserAccountAndLikeTypeAndTargetId(userAccount, likeType, targetId)
                .orElseThrow(() -> new PreOrderApplicationException(ErrorCode.INVALID_LIKE_REQUEST, String.format("user is %s and likeType is %s and targetId is %d", email, likeType, targetId)));

        //TODO: else 를 return으로 대체할 수 있게 리팩토링
        if (likes != null) {
            likes.toggleDeleted();
            likesRepository.save(likes);
        } else {
            likesRepository.save(Likes.of(userAccount, likeType, targetId));
        }
    }
}
