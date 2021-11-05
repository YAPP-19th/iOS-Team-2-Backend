package com.yapp.project.common.web;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseMessage {
    MEMBER_INSERT_SUCCESS(HttpStatus.CREATED);

    private final HttpStatus status;

    ResponseMessage(HttpStatus status) {
        this.status = status;
    }
}
