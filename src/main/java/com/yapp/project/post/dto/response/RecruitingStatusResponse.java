package com.yapp.project.post.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RecruitingStatusResponse {
    private final int positionCode;

    private final int skillCode;

    private final String status;
}
