package com.yapp.project.review.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TextReviewCreateRequest {
    private Long postId;

    private Long targetMemberId;

    private String title;

    private String content;
}
