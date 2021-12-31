package com.yapp.project.apply.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApplyResponse {
    private final long applyId;

    private final String applyStatus;

    private final long recruitingPositionId;

    private final long postId;
}
