package com.yapp.project.post.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RecruitingStatusResponse {
    private final Long recruitingPositionId;

    private final String  positionName;

    private final int  positionCode;

    private final String skillName;

    private final int skillCode;

    private final String status;
}
