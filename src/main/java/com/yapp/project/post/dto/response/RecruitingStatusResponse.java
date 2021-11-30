package com.yapp.project.post.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RecruitingStatusResponse {
    List<RecruitingStatus> RecruitingStatuses;

    @Getter
    @RequiredArgsConstructor
    public static class RecruitingStatus{
        private final Long recruitingPositionId;

        private final String  positionName;

        private final int  positionCode;

        private final String skillName;

        private final int skillCode;

        private final String status;
    }
}
