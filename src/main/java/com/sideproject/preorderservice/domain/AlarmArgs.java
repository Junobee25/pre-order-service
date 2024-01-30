package com.sideproject.preorderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlarmArgs {

    // 알람을 발생시킨 사람
    private Long fromUserId;

    // 알람의 주체
    private Long targetId;
}
