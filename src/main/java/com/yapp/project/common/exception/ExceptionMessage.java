package com.yapp.project.common.exception;

import com.yapp.project.common.StatusCode;
import lombok.Getter;

@Getter
public enum ExceptionMessage {
    // internal server error
    FILE_CONVERTION_FAIL_EXCEPTION(StatusCode.FILE_CONVERTION_FAIL_EXCEPTION),

    // bad request / not found
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
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(StatusCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED),
    NOT_EXIST_POSITION_CODE_IN_DB(StatusCode.NOT_EXIST_POSITION_CODE_IN_DB),
    NOT_EXIST_ROOT_POSITION_CODE(StatusCode.NOT_EXIST_ROOT_POSITION_CODE),
    NOT_EXIST_ROOT_POSITION_NAME(StatusCode.NOT_EXIST_ROOT_POSITION_NAME),
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

    // 그외 모든 예외
    ALL_OTHER_EXCEPTIONS(StatusCode.ALL_OTHER_EXCEPTIONS),

    ;

    private final StatusCode statusCode; //TODO: statusCode 커스터마이징 할 지 회의

    ExceptionMessage(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
