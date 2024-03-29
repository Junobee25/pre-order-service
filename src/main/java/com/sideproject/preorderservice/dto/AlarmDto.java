package com.sideproject.preorderservice.dto;

import com.sideproject.preorderservice.domain.constant.AlarmType;
import com.sideproject.preorderservice.domain.entity.UserAccount;
import com.sideproject.preorderservice.domain.entity.Alarm;

import java.time.LocalDateTime;

public record AlarmDto(
        Long id,
        UserAccount userAccount,
        Long fromUserId,
        Long targetId,
        AlarmType alarmType,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static AlarmDto fromEntity(Alarm entity) {
        return new AlarmDto(
                entity.getId(),
                entity.getUserAccount(),
                entity.getFromUserId(),
                entity.getTargetId(),
                entity.getAlarmType(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }
}
