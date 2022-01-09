package com.yapp.project.notification.entity.value;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum NotificationType {
    APPLY(2),
    INVITE(4),

    ;

    private int code;

    NotificationType(int code) {
        this.code = code;
    }

    public static NotificationType of(int code) {
        return Arrays.stream(NotificationType.values())
                .filter(v -> v.code == code)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_NOTIFICATION_CODE));
    }
}
