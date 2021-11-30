package com.yapp.project.common.exception;

import com.yapp.project.common.StatusCode;
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
        return createApiExceptionResult(StatusCode.DTO_VALIDATION_FAIL, exception.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(BindException.class)
    public ApiExceptionResult handleBindException(BindException exception){
        return createApiExceptionResult(StatusCode.DTO_VALIDATION_FAIL, exception.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    protected ApiExceptionResult handleNotFoundException(NotFoundException exception) {
        return createApiExceptionResult(exception);
    }

//    /**
//     * @ModelAttribut 으로 binding error 발생시 BindException 발생한다.
//     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
//     */
//    @ExceptionHandler(BindException.class)
//    protected ApiExceptionResult handleBindException(BindException exception) {
//        return createApiExceptionResult(StatusCode.DATA_BINDING_FAIL, ExceptionMessage.DATA_BINDING_FAIL.name());
//    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ApiExceptionResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return createApiExceptionResult(StatusCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED, ExceptionMessage.HTTP_REQUEST_METHOD_NOT_SUPPORTED.name());
    }

//    @ExceptionHandler(Exception.class)
//    protected ApiExceptionResult handleException(Exception exception) {
//        return createApiExceptionResult(StatusCode.ALL_OTHER_EXCEPTIONS, ExceptionMessage.ALL_OTHER_EXCEPTIONS.name());
//    }

    private ApiExceptionResult createApiExceptionResult(Exception exception) {
        ExceptionMessage exceptionMessage = ExceptionMessage.valueOf(exception.getMessage());
        return ApiExceptionResult.of(exceptionMessage);
    }

    private ApiExceptionResult createApiExceptionResult(StatusCode statusCode, String message) {
        return ApiExceptionResult.of(statusCode, message);
    }
}
