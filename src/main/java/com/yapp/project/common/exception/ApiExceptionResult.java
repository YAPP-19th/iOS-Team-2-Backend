package com.yapp.project.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiExceptionResult {
    private final int statusCode;
    private final String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime serverDateTime;

    private ApiExceptionResult(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.serverDateTime = LocalDateTime.now();
    }

    public static ApiExceptionResult of(ExceptionMessage exceptionMessage) {
        return new ApiExceptionResult(exceptionMessage.getStatus().value(), exceptionMessage.name());
    }
}
