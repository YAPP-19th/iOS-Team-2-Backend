package com.yapp.project.common.exception;

import com.yapp.project.common.exception.type.NotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiExceptionResult handleMethodValidException(MethodArgumentNotValidException exception){
        return createApiExceptionResult(4000, exception.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    protected ApiExceptionResult handleNotFoundException(NotFoundException exception) {
        return createApiExceptionResult(exception);
    }

    /**
     * @ModelAttribut 으로 binding error 발생시 BindException 발생한다.
     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
    @ExceptionHandler(BindException.class)
    protected ApiExceptionResult handleBindException(BindException exception) {
        //final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return createApiExceptionResult(4321, ExceptionMessage.DATA_BINDING_FAIL.name());
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ApiExceptionResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return createApiExceptionResult(9999, ExceptionMessage.HTTP_REQUEST_METHOD_NOT_SUPPORTED.name());
    }

    @ExceptionHandler(Exception.class)
    protected ApiExceptionResult handleException(Exception exception) {
        return createApiExceptionResult(0000, ExceptionMessage.DEFAULT_EXCEPTION.name());
    }

    private ApiExceptionResult createApiExceptionResult(Exception exception) {
        ExceptionMessage exceptionMessage = ExceptionMessage.valueOf(exception.getMessage());
        return ApiExceptionResult.of(exceptionMessage);
    }

    private ApiExceptionResult createApiExceptionResult(int httpStatus, String message) {
        return ApiExceptionResult.of(httpStatus, message);
    }
}
