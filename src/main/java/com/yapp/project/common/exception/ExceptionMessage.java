package com.yapp.project.common.exception;

import com.yapp.project.common.StatusCode;
import lombok.Getter;

@Getter
public enum ExceptionMessage {
    // internal server error
    FILE_CONVERTION_FAIL_EXCEPTION(StatusCode.FILE_CONVERTION_FAIL_EXCEPTION),
    CLOUD_FAIL(StatusCode.CLOUD_FAIL),

    // bad request / not found
    ACCESS_DENIED(StatusCode.BAD_REQUEST),
    BAD_REQUEST(StatusCode.BAD_REQUEST),
    MEMBER_NOT_FOUND(StatusCode.MEMBER_NOT_FOUND),
    NOT_EXIST_POST_STATUS(StatusCode.NOT_EXIST_POST_STATUS),
    NOT_EXIST_POSITION_CODE(StatusCode.NOT_EXIST_POSITION_CODE),
    NOT_EXIST_SKILL_CODE(StatusCode.NOT_EXIST_SKILL_CODE),
    NOT_EXIST_POST_CATEGORY_CODE(StatusCode.NOT_EXIST_POST_CATEGORY_CODE),
    NOT_EXIST_MEMBER_ID(StatusCode.NOT_EXIST_MEMBER_ID),
    NOT_EXIST_POST_ID(StatusCode.NOT_EXIST_POST_ID),
    POST_ID_AND_RECRUITING_POSITION_MISMATCH(StatusCode.POST_ID_AND_RECRUITING_POSITION_MISMATCH),
    NOT_EXIST_POST_CATEGORY_NAME(StatusCode.NOT_EXIST_POST_CATEGORY_NAME),
    NOT_EXIST_POSITION_NAME(StatusCode.NOT_EXIST_POSITION_NAME),
    NOT_EXIST_SKILL_NAME(StatusCode.NOT_EXIST_SKILL_NAME),
    NOT_EXIST_RECRUITING_POSITION_ID(StatusCode.NOT_EXIST_RECRUITING_POSITION_ID),
    NOT_EXIST_APPROVAL_STATUS_CODE(StatusCode.NOT_EXIST_APPROVAL_STATUS_CODE),
    NOT_EXIST_APPROVAL_STATUS_NAME(StatusCode.NOT_EXIST_APPROVAL_STATUS_NAME),
    NOT_EXIST_POST_ONLINE_STATUS_CODE(StatusCode.NOT_EXIST_POST_ONLINE_STATUS_CODE),
    NOT_EXIST_POST_ONLINE_STATUS_NAME(StatusCode.NOT_EXIST_POST_ONLINE_STATUS_NAME),
    DATA_BINDING_FAIL(StatusCode.DATA_BINDING_FAIL),
    INVALID_HTTP_REQUEST(StatusCode.INVALID_HTTP_REQUEST),
    NOT_EXIST_POSITION_CODE_IN_DB(StatusCode.NOT_EXIST_POSITION_CODE_IN_DB),
    NOT_EXIST_BASE_POSITION_CODE(StatusCode.NOT_EXIST_BASE_POSITION_CODE),
    NOT_EXIST_BASE_POSITION_NAME(StatusCode.NOT_EXIST_BASE_POSITION_NAME),
    POST_ID_AND_MEMBER_ID_MISMATCH(StatusCode.POST_ID_AND_MEMBER_ID_MISMATCH),
    NOT_EXIST_REVIEW_CODE(StatusCode.NOT_EXIST_REVIEW_CODE),
    NOT_EXIST_REVIEW_NAME(StatusCode.NOT_EXIST_REVIEW_NAME),
    EXCEEDED_APPLICANTS_NUMBER(StatusCode.EXCEEDED_APPLICANTS_NUMBER),
    NOT_EXIST_APPLY_STATUS_CODE(StatusCode.NOT_EXIST_APPLY_STATUS_CODE),
    NOT_EXIST_APPLY_STATUS_NAME(StatusCode.NOT_EXIST_APPLY_STATUS_NAME),
    NOT_EXIST_APPLY_ID(StatusCode.NOT_EXIST_APPLY_ID),
    ALREADY_LIKE_POST(StatusCode.ALREADY_LIKE_POST),
    LIKE_POST_YET(StatusCode.LIKE_POST_YET),
    ALREADY_LIKE_MEMBER(StatusCode.ALREADY_LIKE_MEMBER),
    LIKE_MEMBER_YET(StatusCode.LIKE_MEMBER_YET),
    NOT_AVAILABLE_NICKNAME(StatusCode.NOT_AVAILABLE_NICKNAME),
    INVALID_IMAGE_EXCEPTION(StatusCode.INVALID_IMAGE_EXCEPTION),
    INVALID_JWT_STRINGS(StatusCode.INVALID_JWT_STRINGS),
    ALREADY_REVIEWED(StatusCode.ALREADY_REVIEWED),
    NO_SELF_REVIEW(StatusCode.NO_SELF_REVIEW),
    ACCESS_TOKEN_IS_EMPTY(StatusCode.ACCESS_TOKEN_IS_EMPTY),
    INVALID_REQUEST_ARGUMENT_TYPE(StatusCode.INVALID_REQUEST_ARGUMENT_TYPE),
    MISSING_PARAMETER(StatusCode.MISSING_PARAMETER),
    FILE_SIZE_LIMIT_EXCEEDED(StatusCode.FILE_SIZE_LIMIT_EXCEEDED),
    ILLEGAL_REVIEWER(StatusCode.ILLEGAL_REVIEWER),
    ILLEGAL_TARGETMEMBER(StatusCode.ILLEGAL_TARGETMEMBER),
    NOT_APPROVED_APPLY(StatusCode.NOT_APPROVED_APPLY),
    INVALID_MEMBER(StatusCode.INVALID_MEMBER),
    INVALID_LIKE_COUNT(StatusCode.INVALID_LIKE_COUNT),
    INVALID_APPLICANT(StatusCode.INVALID_APPLICANT),
    ALREADY_APPLIED(StatusCode.ALREADY_APPLIED),
    NOT_EXIST_SELECT_REVIEW(StatusCode.NOT_EXIST_SELECT_REVIEW),
    NOT_EXIST_NOTIFICATION_CODE(StatusCode.NOT_EXIST_NOTIFICATION_CODE),

    // 그외 모든 예외
    ALL_OTHER_EXCEPTIONS(StatusCode.ALL_OTHER_EXCEPTIONS),
    UNEXPECTED_EXCEPTIONS(StatusCode.UNEXPECTED_EXCEPTIONS),
    JWT_SIGNATURE_DOES_NOT_MATCH(StatusCode.JWT_SIGNATURE_DOES_NOT_MATCH),
    UNABLE_SEND_NOTIFICATION(StatusCode.UNABLE_SEND_NOTIFICATION),

    ;

    private final StatusCode statusCode; //TODO: statusCode 커스터마이징 할 지 회의

    ExceptionMessage(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
