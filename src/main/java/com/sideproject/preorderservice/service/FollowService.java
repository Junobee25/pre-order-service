package com.sideproject.preorderservice.service;

import com.sideproject.preorderservice.configuration.AlarmType;
import com.sideproject.preorderservice.domain.Follow;
import com.sideproject.preorderservice.domain.UserAccount;
import com.sideproject.preorderservice.domain.entity.AlarmEntity;
import com.sideproject.preorderservice.exception.ErrorCode;
import com.sideproject.preorderservice.exception.PreOrderApplicationException;
import com.sideproject.preorderservice.repository.AlarmEntityRepository;
import com.sideproject.preorderservice.repository.FollowRepository;
import com.sideproject.preorderservice.repository.UserAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserAccountRepository userAccountRepository;
    private final AlarmEntityRepository alarmEntityRepository;
    private final FollowRepository followRepository;

    public void follow(String fromUser, String toUser) {
        // user find
        UserAccount fromUserAccount = userAccountRepository.findByEmail(fromUser).orElseThrow(() ->
                new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", fromUser)));
        UserAccount toUserAccount = userAccountRepository.findByEmail(toUser).orElseThrow(() ->
                new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", toUser)));

        alarmEntityRepository.save(AlarmEntity.of(toUserAccount, fromUserAccount.getId(), toUserAccount.getId(), AlarmType.NEW_FOLLOW_TO_USER));
        followRepository.save(Follow.of(fromUserAccount, toUserAccount));
    }

}
