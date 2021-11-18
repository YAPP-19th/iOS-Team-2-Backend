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

    private final int onlineStatusCode;
    private final String onlineStatusName;

    OnlineStatus(int onlineStatusCode, String onlineStatusName) {
        this.onlineStatusCode = onlineStatusCode;
        this.onlineStatusName = onlineStatusName;
    }

    public static OnlineStatus of(int onlineStatusCode) {
        return Arrays.stream(OnlineStatus.values())
                .filter(v -> v.onlineStatusCode == onlineStatusCode)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ONLINE_STATUS_CODE));
    }

    public static OnlineStatus of(String onlineStatusName) {
        return Arrays.stream(OnlineStatus.values())
                .filter(v -> v.onlineStatusName.equals(onlineStatusName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ONLINE_STATUS_NAME));
    }
}
