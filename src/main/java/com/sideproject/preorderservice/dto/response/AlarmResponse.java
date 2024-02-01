package com.sideproject.preorderservice.dto.response;

import com.sideproject.preorderservice.domain.constant.AlarmType;
import com.sideproject.preorderservice.dto.AlarmDto;
import com.sideproject.preorderservice.dto.UserAccountDto;

import java.time.LocalDateTime;

public record AlarmResponse(
        Long id,
        UserAccountResponse userAccountResponse,
        Long fromUserId,
        Long targetId,
        AlarmType alarmType,
        String text,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static AlarmResponse from(AlarmDto entity) {
        return new AlarmResponse(
                entity.id(),
                UserAccountResponse.from(UserAccountDto.from(entity.userAccount())),
                entity.fromUserId(),
                entity.targetId(),
                entity.alarmType(),
                entity.alarmType().getAlarmType(),
                entity.createdAt(),
                entity.modifiedAt()
        );
    }
}
