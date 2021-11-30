package com.yapp.project.common.web;

import com.yapp.project.common.StatusCode;
import lombok.Getter;

@Getter
public enum ResponseMessage {
    SUCCESS(StatusCode.SUCCESS),

    SUCCESS_SIGN_UP(StatusCode.SUCCESS),
    LOGIN_SUCCESS(StatusCode.SUCCESS),
    CHECK_AVAILABLE_NICKNAME(StatusCode.SUCCESS),
    NOT_AVAILABLE_NICKNAME(StatusCode.NOT_AVAILABLE_NICKNAME),

    ;

    private final StatusCode statusCode;

    ResponseMessage(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
