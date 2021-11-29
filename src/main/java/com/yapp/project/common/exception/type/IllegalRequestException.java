package com.yapp.project.common.exception.type;

import com.yapp.project.common.exception.ExceptionMessage;

public class IllegalRequestException extends RuntimeException{
    public IllegalRequestException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.name());
    }
}
