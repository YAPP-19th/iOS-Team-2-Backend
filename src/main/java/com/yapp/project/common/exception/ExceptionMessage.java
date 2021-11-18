package com.yapp.project.common.exception;

import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
public enum ExceptionMessage {
    EXCEPTION_MESSAGE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND),
    NOT_EXIST_POST_STATUS(HttpStatus.NOT_FOUND),
    NOT_EXIST_POSITION_CODE(HttpStatus.NOT_FOUND),
    NOT_EXIST_SKILL_CODE(HttpStatus.NOT_FOUND),
    NOT_EXIST_POST_CATEGORY_CODE(HttpStatus.NOT_FOUND),
    NOT_EXIST_MEMBER_ID(HttpStatus.NOT_FOUND),
    FILE_CONVERTION_FAIL_EXCEPTION(HttpStatus.BAD_REQUEST),
    NOT_EXIST_POST_ID(HttpStatus.NOT_FOUND),
    POST_ID_AND_RECRUITING_POSITION_MISMATCH(HttpStatus.NOT_FOUND),
    NOT_EXIST_POST_CATEGORY_NAME(HttpStatus.NOT_FOUND),
    NOT_EXIST_POSITION_NAME(HttpStatus.NOT_FOUND),
    NOT_EXIST_SKILL_NAME(HttpStatus.NOT_FOUND),
    NOT_EXIST_RECRUITING_POSITION_ID(HttpStatus.BAD_REQUEST),
    NOT_EXIST_APPROVAL_STATUS_CODE(HttpStatus.NOT_FOUND),
    NOT_EXIST_APPROVAL_STATUS_NAME(HttpStatus.NOT_FOUND),
    NOT_EXIST_POST_ONLINE_STATUS_CODE(HttpStatus.NOT_FOUND),
    NOT_EXIST_POST_ONLINE_STATUS_NAME(HttpStatus.NOT_FOUND),
    DATA_BINDING_FAIL(HttpStatus.BAD_REQUEST),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(HttpStatus.BAD_REQUEST),
    DEFAULT_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR);

    private final HttpStatus status; //TODO: statusCode 커스터마이징 할 지 회의

    ExceptionMessage(HttpStatus status) {
        this.status = status;
    }

    public static ExceptionMessage of(String meeage) {
        return Arrays.stream(values())
                .filter(e -> e.name().equals(meeage))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.EXCEPTION_MESSAGE_NOT_FOUND));
    }
}
