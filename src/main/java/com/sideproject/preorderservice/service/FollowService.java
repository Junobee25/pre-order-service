package com.sideproject.preorderservice.service;

import com.sideproject.preorderservice.domain.constant.AlarmType;
import com.sideproject.preorderservice.domain.entity.Alarm;
import com.sideproject.preorderservice.domain.entity.Follow;
import com.sideproject.preorderservice.domain.entity.UserAccount;
import com.sideproject.preorderservice.exception.ErrorCode;
import com.sideproject.preorderservice.exception.PreOrderApplicationException;
import com.sideproject.preorderservice.repository.AlarmEntityRepository;
import com.sideproject.preorderservice.repository.FollowRepository;
import com.sideproject.preorderservice.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserAccountRepository userAccountRepository;
    private final AlarmEntityRepository alarmEntityRepository;
    private final FollowRepository followRepository;

    public void follow(String fromUser, String toUser) {
        UserAccount fromUserAccount = userAccountRepository.findByEmail(fromUser).orElseThrow(() ->
                new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", fromUser)));
        UserAccount toUserAccount = userAccountRepository.findByEmail(toUser).orElseThrow(() ->
                new PreOrderApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", toUser)));

        Optional<Follow> existingFollow = followRepository.findByFromUserIdAndToUserId(fromUserAccount.getId(), toUserAccount.getId());

        if (existingFollow.isPresent()) {
            followRepository.delete(existingFollow.get());
        } else {
            alarmEntityRepository.save(Alarm.of(toUserAccount, fromUserAccount.getId(), toUserAccount.getId(), AlarmType.NEW_FOLLOW_TO_USER));
            followRepository.save(Follow.of(fromUserAccount, toUserAccount));
        }
    }
}
