package com.yapp.project.common.web;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseMessage {
    POST_INSERT_SUCCESS(HttpStatus.CREATED),
    POST_SEARCH_SUCCESS(HttpStatus.OK),
    POST_DELETE_SUCCESS(HttpStatus.OK);

    private final HttpStatus status;

    ResponseMessage(HttpStatus status) {
        this.status = status;
    }
}
