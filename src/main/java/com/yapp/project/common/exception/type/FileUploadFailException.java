package com.yapp.project.common.exception.type;

import com.yapp.project.common.exception.ExceptionMessage;

public class FileUploadFailException extends RuntimeException{
    public FileUploadFailException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.name());
    }
}
