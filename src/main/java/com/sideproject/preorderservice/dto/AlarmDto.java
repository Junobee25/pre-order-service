package com.sideproject.preorderservice.dto;

import com.sideproject.preorderservice.configuration.AlarmType;
import com.sideproject.preorderservice.domain.AlarmArgs;
import com.sideproject.preorderservice.domain.UserAccount;
import com.sideproject.preorderservice.domain.entity.AlarmEntity;

import java.time.LocalDateTime;

public record AlarmDto(
        Long id,
        UserAccount userAccount,
        AlarmType alarmType,
        AlarmArgs alarmArgs,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static AlarmDto fromEntity(AlarmEntity entity) {
        return new AlarmDto(
                entity.getId(),
                entity.getUserAccount(),
                entity.getAlarmType(),
                entity.getArgs(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }
}
