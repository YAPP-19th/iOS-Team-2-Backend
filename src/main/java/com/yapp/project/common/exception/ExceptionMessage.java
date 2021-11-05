package com.yapp.project.common.exception;

import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
public enum ExceptionMessage {
    EXCEPTION_MESSAGE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND);

    private final HttpStatus status;

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
