package com.yapp.project.review.dto.request;

import com.yapp.project.common.exception.DtoValidationFailMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class TextReviewCreateRequest {
    private Long postId;

    @Min(value = 1, message = DtoValidationFailMessage.INVALID_TARGET_MEMBER)
    private Long targetMemberId;

    @Size(min = 2, max = 255, message = DtoValidationFailMessage.INVALID_REVIEW_TITLE)
    private String title;

    @Size(min = 2, max = 255, message = DtoValidationFailMessage.INVALID_REVIEW_CONTENT)
    private String content;
}
