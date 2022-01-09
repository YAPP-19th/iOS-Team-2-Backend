package com.yapp.project.review.dto.response;

import com.yapp.project.common.dto.PositionAndColor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class TeamResponse {
    private final Long id;

    private final String nickName;

    private final String profileImageUrl;

    private final String address;

    private final PositionAndColor position;

    private final Boolean isAlreadyReviewedFromCurrentMember;
}
