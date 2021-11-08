package com.yapp.project.common.exception;

import com.yapp.project.common.exception.type.InvalidRequestBodyFieldException;
import com.yapp.project.common.exception.type.NotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiExceptionResult handleMethodValidException(MethodArgumentNotValidException exception){
        return createApiExceptionResult(400, new InvalidRequestBodyFieldException(exception.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    protected ApiExceptionResult handleNotFoundException(NotFoundException exception) {
        return createApiExceptionResult(exception);
    }

    private ApiExceptionResult createApiExceptionResult(Exception exception) {
        ExceptionMessage exceptionMessage = ExceptionMessage.valueOf(exception.getMessage());
        return ApiExceptionResult.of(exceptionMessage);
    }

    private ApiExceptionResult createApiExceptionResult(int httpStatus, Exception exception) {
        return ApiExceptionResult.of(httpStatus, exception.getMessage());
    }
}
