package com.yapp.project.common.exception;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.yapp.project.common.StatusCode;
import com.yapp.project.common.exception.type.NotFoundException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

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

    @ExceptionHandler(MalformedJwtException.class)
    protected ApiExceptionResult handleMalformedJwtException(MalformedJwtException exception) {
        return createApiExceptionResult(ExceptionMessage.INVALID_JWT_STRINGS);
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
        return createApiExceptionResult(ExceptionMessage.HTTP_REQUEST_METHOD_NOT_SUPPORTED);
    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ApiExceptionResult handleAmazonS3Exception(AmazonS3Exception exception) {
        return createApiExceptionResult(ExceptionMessage.CLOUD_FAIL);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiExceptionResult handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        return createApiExceptionResult(ExceptionMessage.INVALID_REQUEST_ARGUMENT_TYPE);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiExceptionResult handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        return createApiExceptionResult(ExceptionMessage.MISSING_PARAMETER);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ApiExceptionResult handleFileSizeLimitExceededException(MaxUploadSizeExceededException exception) {
        return createApiExceptionResult(ExceptionMessage.FILE_SIZE_LIMIT_EXCEEDED);
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

    private ApiExceptionResult createApiExceptionResult(ExceptionMessage exceptionMessage) {
        return ApiExceptionResult.of(exceptionMessage);
    }
}
