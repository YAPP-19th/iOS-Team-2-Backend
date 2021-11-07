package com.yapp.project.common.exception.type;

public class InvalidRequestBodyFieldException extends RuntimeException {
    public InvalidRequestBodyFieldException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
