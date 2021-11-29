package com.yapp.project.common.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResult<T> {
    private final int statusCode;
    private final String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime serverDateTime;

    private final T data;

    private ApiResult(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.serverDateTime = LocalDateTime.now();
    }

    public static <T> ApiResult<T> of(ResponseMessage responseMessage, T data) {
        return new ApiResult(
                responseMessage.getStatusCode().getStatusCode(),
                responseMessage.name(),
                data
        );
    }

    public static <T> ApiResult<T> of(ResponseMessage responseMessage) {
        return new ApiResult(
                responseMessage.getStatusCode().getStatusCode(),
                responseMessage.name(),
                null
        );
    }
}
