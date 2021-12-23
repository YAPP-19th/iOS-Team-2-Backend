package com.yapp.project.review.dto.response;

import com.yapp.project.review.entity.value.ReviewCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CodeReviewResponse {
    private Integer reviewCode;
    private Long count ;
    private String reviewText;

    public void setReviewText(int reviewCode){
        this.reviewText = ReviewCode.of(reviewCode).getReviewName();
    }
}
