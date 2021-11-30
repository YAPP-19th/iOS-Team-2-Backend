package com.yapp.project.review.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CodeReviewInsertRequest {
    private Long postId;

    private List<String> selectedReviews;
}
