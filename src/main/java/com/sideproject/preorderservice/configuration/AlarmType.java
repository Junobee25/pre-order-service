package com.sideproject.preorderservice.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmType {
    NEW_POST("new post"),
    NEW_COMMENT_ON_POST("new comment"),
    NEW_LIKE_ON_POST("new like"),
    NEW_LIKE_ON_COMMENT("new like"),
    NEW_FOLLOW_TO_USER("new follow");

    private final String alarmType;
}
