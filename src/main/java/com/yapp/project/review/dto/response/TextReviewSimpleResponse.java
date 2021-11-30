package com.yapp.project.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TextReviewSimpleResponse {
    private Long postId;

    private Long reviewerId;

    private String title;

    private String content;
}
