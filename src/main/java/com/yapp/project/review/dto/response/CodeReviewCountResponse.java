package com.yapp.project.review.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class CodeReviewCountResponse {
    private final Map<String, Integer> positives = new HashMap<>();

    private Map<String, Integer> negatives = new HashMap<>();
}
