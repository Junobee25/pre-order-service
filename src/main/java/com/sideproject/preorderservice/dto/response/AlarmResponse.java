package com.sideproject.preorderservice.dto.response;

import com.sideproject.preorderservice.configuration.AlarmType;
import com.sideproject.preorderservice.domain.AlarmArgs;
import com.sideproject.preorderservice.dto.AlarmDto;
import com.sideproject.preorderservice.dto.UserAccountDto;

import java.time.LocalDateTime;

public record AlarmResponse(
        Long id,
        UserAccountResponse userAccountResponse,
        AlarmType alarmType,
        AlarmArgs alarmArgs,
        String text,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static AlarmResponse fromAlarm(AlarmDto entity) {
        return new AlarmResponse(
                entity.id(),
                UserAccountResponse.fromUser(UserAccountDto.from(entity.userAccount())),
                entity.alarmType(),
                entity.alarmArgs(),
                entity.alarmType().getAlarmTest(),
                entity.createdAt(),
                entity.modifiedAt()
        );
    }
}