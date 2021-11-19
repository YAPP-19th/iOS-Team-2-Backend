package com.yapp.project.common.web;

import com.yapp.project.common.StatusCode;
import lombok.Getter;

@Getter
public enum ResponseMessage {
    POST_INSERT_SUCCESS(StatusCode.SUCCESS),
    POST_SEARCH_SUCCESS(StatusCode.SUCCESS),
    POST_DELETE_SUCCESS(StatusCode.SUCCESS);

    private final StatusCode statusCode;

    ResponseMessage(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
