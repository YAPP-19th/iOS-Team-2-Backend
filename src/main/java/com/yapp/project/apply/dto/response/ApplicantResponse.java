package com.yapp.project.apply.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class ApplicantResponse {
    private final long applyId;

    private final ApplyerResponse applyer;

    private final RecruitingPositionResponse recruitingPositionResponse;

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class ApplyerResponse{
        private final long id;

        private final String profileImageUrl;

        private final String nickName;

        private final String address;

        private final PositionAndColor position;

        private final Boolean isApproved;
    }

    @Getter
    @RequiredArgsConstructor
    public static class RecruitingPositionResponse{
        private final long id;

        private final String position;
    }

    @Getter
    @RequiredArgsConstructor
    public static class PositionAndColor {
        private final String position;

        private final int colorCode;
    }
}
