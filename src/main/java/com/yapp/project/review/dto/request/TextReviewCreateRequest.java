package com.yapp.project.review.dto.request;

import com.yapp.project.common.exception.DtoValidationFailMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TextReviewCreateRequest {
    @Size(min = 2, max = 255, message = DtoValidationFailMessage.INVALID_REVIEW_TITLE)
    private String title;

    @Size(min = 2, max = 1000, message = DtoValidationFailMessage.INVALID_REVIEW_CONTENT)
    private String content;
}
