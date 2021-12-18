package com.yapp.project.review.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TextReviewSimpleResponse {
    private final Long postId;

    private final Long reviewerId;

    private final String title;

    private final String content;
}
