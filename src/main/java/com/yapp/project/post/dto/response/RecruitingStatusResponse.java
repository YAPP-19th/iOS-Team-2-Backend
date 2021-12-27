package com.yapp.project.post.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class RecruitingStatusResponse {
    private final List<RecruitingStatus> RecruitingStatuses = new ArrayList<>();

    @Getter
    @RequiredArgsConstructor
    public static class RecruitingStatus{
        private final Long recruitingPositionId;

        private final PositionAndColor positions;

        private final String status;
    }
}
