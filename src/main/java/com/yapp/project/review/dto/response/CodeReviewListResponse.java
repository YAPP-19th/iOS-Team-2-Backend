package com.yapp.project.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CodeReviewListResponse {
    private final List<String> positives;

    private final List<String> negatives;
}
