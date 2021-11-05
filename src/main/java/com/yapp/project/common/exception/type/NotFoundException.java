package com.yapp.project.common.exception.type;

import com.yapp.project.common.exception.ExceptionMessage;

public class NotFoundException extends RuntimeException{
    public NotFoundException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.name());
    }
}
