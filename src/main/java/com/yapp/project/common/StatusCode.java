package com.yapp.project.common;

import lombok.Getter;

@Getter
public enum StatusCode {
    // success
    SUCCESS(1000),

    // internal server error
    INTERNAL_SERVER_ERROR(5000),
    FILE_CONVERTION_FAIL_EXCEPTION(5001),
    CLOUD_FAIL(5555),

    // bad request / not found
    BAD_REQUEST(4000),
    SERVICE_NOT_FOUND(4001),
    MEMBER_NOT_FOUND(4001),
    NOT_EXIST_POST_STATUS(4002),
    NOT_EXIST_POSITION_CODE(4003),
    NOT_EXIST_SKILL_CODE(4004),
    NOT_EXIST_POST_CATEGORY_CODE(4005),
    NOT_EXIST_MEMBER_ID(4006),
    NOT_EXIST_POST_ID(4007),
    POST_ID_AND_RECRUITING_POSITION_MISMATCH(4008),
    NOT_EXIST_POST_CATEGORY_NAME(4009),
    NOT_EXIST_POSITION_NAME(4010),
    NOT_EXIST_SKILL_NAME(4011),
    NOT_EXIST_RECRUITING_POSITION_ID(4012),
    NOT_EXIST_APPROVAL_STATUS_CODE(4013),
    NOT_EXIST_APPROVAL_STATUS_NAME(4014),
    NOT_EXIST_POST_ONLINE_STATUS_CODE(4015),
    NOT_EXIST_POST_ONLINE_STATUS_NAME(4016),
    NOT_EXIST_POSITION_CODE_IN_DB(4017),
    NOT_EXIST_ROOT_POSITION_CODE(4018),
    NOT_EXIST_ROOT_POSITION_NAME(4019),
    DATA_BINDING_FAIL(4020),  // dto에 값 바인딩 실패
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(4021),
    DTO_VALIDATION_FAIL(4022),
    EXCEEDED_APPLICANTS_NUMBER(4023),
    POST_ID_AND_MEMBER_ID_MISMATCH(4024),
    NOT_EXIST_APPLY_STATUS_CODE(4025),
    NOT_EXIST_APPLY_STATUS_NAME(4026),
    ALREADY_LIKE_POST(4027),
    LIKE_POST_YET(4028),
    NOT_EXIST_REVIEW_CODE(4029),
    NOT_EXIST_REVIEW_NAME(4030),
    NOT_EXIST_APPLY_ID(4031),
    ALREADY_LIKE_MEMBER(4032),
    LIKE_MEMBER_YET(4033),
    NOT_AVAILABLE_NICKNAME(4034),
    INVALID_IMAGE_EXCEPTION(4035),
    INVALID_JWT_STRINGS(4036),
    ALREADY_REVIEWED(4037),
    NO_SELF_REVIEW(4038),
    ACCESS_TOKEN_IS_EMPTY(4039),
    INVALID_REQUEST_ARGUMENT_TYPE(4040),
    MISSING_PARAMETER(4041),
    FILE_SIZE_LIMIT_EXCEEDED(4042),
  
    // 지원하지 않는 API에 대한 access 시 발생
    NOT_IMPLEMENTED(0),

    // 그외 모든 예외
    ALL_OTHER_EXCEPTIONS(9999),

    ;

    private final int statusCode;

    StatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
