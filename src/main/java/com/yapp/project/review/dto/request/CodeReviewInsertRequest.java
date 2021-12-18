package com.yapp.project.review.dto.request;

import com.yapp.project.common.exception.DtoValidationFailMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@AllArgsConstructor
public class CodeReviewInsertRequest {
    @Min(value = 1, message = DtoValidationFailMessage.INVALID_POST_ID)
    private Long postId;

    @Size(min = 1, message = DtoValidationFailMessage.EMPTY_SELECTED_REVIEW)
    private List<String> selectedReviews;
}
