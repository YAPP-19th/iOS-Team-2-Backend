package com.yapp.project.post.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RecruitingStatusResponse {
    private final String  positionName;

    private final String skillName;

    private final String status;
}
