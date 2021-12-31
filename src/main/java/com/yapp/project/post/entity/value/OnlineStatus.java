package com.yapp.project.post.entity.value;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OnlineStatus {
    DEFAULT(0, "미정"),
    ONLINE(2, "온라인"),
    OFFLINE(4, "오프라인"),
    ONOFFLINE(8, "온오프라인");

    private final int code;
    private final String name;

    OnlineStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static OnlineStatus of(int onlineStatusCode) {
        return Arrays.stream(OnlineStatus.values())
                .filter(v -> v.code == onlineStatusCode)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ONLINE_STATUS_CODE));
    }

    public static OnlineStatus of(String onlineStatusName) {
        return Arrays.stream(OnlineStatus.values())
                .filter(v -> v.name.equals(onlineStatusName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ONLINE_STATUS_NAME));
    }
}
