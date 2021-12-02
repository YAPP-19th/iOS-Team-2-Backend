package com.yapp.project.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class RecruitingStatusResponse {
    private List<RecruitingStatus> RecruitingStatuses = new ArrayList<>();

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
