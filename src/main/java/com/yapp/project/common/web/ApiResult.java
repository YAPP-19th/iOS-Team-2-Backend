package com.yapp.project.common.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;

@Getter
public class ApiResult<T> {
    private final int statusCode;
    private final String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime serverDateTime;

    private final T data;
    private final T link;


    private ApiResult(int statusCode, String message, T data, T link) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.link = link;
        this.serverDateTime = LocalDateTime.now();
    }

    public static <T> ApiResult<T> of(ResponseMessage responseMessage, EntityModel<T> entityModel) {
        return new ApiResult(
                responseMessage.getStatus().value(),
                responseMessage.name(),
                entityModel.getContent(),
                entityModel.getLinks()
        );
    }
}
