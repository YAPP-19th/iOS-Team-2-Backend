package com.yapp.project.common.web;

import com.yapp.project.common.StatusCode;
import lombok.Getter;

@Getter
public enum ResponseMessage {
    SUCCESS(StatusCode.SUCCESS),

    SUCCESS_SIGN_UP(StatusCode.SUCCESS),
    LOGIN_SUCCESS(StatusCode.SUCCESS),

    ;

    private final StatusCode statusCode;

    ResponseMessage(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
