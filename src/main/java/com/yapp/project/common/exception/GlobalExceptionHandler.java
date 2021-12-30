package com.yapp.project.common.exception;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.yapp.project.common.StatusCode;
import com.yapp.project.common.exception.type.FileUploadFailException;
import com.yapp.project.common.exception.type.IllegalRequestException;
import com.yapp.project.common.exception.type.NotFoundException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolationException;
import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * from @Valid
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiExceptionResult handleMethodValidException(MethodArgumentNotValidException exception){
        return createApiExceptionResult(StatusCode.DTO_VALIDATION_FAIL, exception.getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * from @Validated
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiExceptionResult handleConstraintViolationException(ConstraintViolationException exception){
        return createApiExceptionResult(StatusCode.DTO_VALIDATION_FAIL, exception.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ApiExceptionResult handleBindException(BindException exception){
        return createApiExceptionResult(StatusCode.DTO_VALIDATION_FAIL, exception.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(MalformedJwtException.class)
    protected ApiExceptionResult handleMalformedJwtException(MalformedJwtException exception) {
        return createApiExceptionResult(ExceptionMessage.INVALID_JWT_STRINGS);
    }

    @ExceptionHandler({
            NotFoundException.class,
            IllegalRequestException.class,
            FileUploadFailException.class
    })
    protected ApiExceptionResult handleDefinedException(RuntimeException exception) {
        return createApiExceptionResult(exception);
    }

    /**
     * 잘못 된 요청 시 발생
     */
    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            MissingRequestHeaderException.class,
            HttpMessageConversionException.class
    })
    protected ApiExceptionResult handleHttpBadRequest(Exception exception) {
        return createApiExceptionResult(ExceptionMessage.INVALID_HTTP_REQUEST);
    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ApiExceptionResult handleAmazonS3Exception(AmazonS3Exception exception) {
        return createApiExceptionResult(ExceptionMessage.CLOUD_FAIL);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiExceptionResult handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        return createApiExceptionResult(ExceptionMessage.INVALID_REQUEST_ARGUMENT_TYPE);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ApiExceptionResult handleFileSizeLimitExceededException(MaxUploadSizeExceededException exception) {
        return createApiExceptionResult(ExceptionMessage.FILE_SIZE_LIMIT_EXCEEDED);
    }

    @ExceptionHandler(SignatureException.class)
    public ApiExceptionResult handleSignatureException(SignatureException exception) {
        return createApiExceptionResult(ExceptionMessage.JWT_SIGNATURE_DOES_NOT_MATCH);
    }

    @ExceptionHandler(Exception.class)
    protected ApiExceptionResult handleException(Exception exception) {
        return createApiExceptionResult(ExceptionMessage.UNEXPECTED_EXCEPTIONS);
    }

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
