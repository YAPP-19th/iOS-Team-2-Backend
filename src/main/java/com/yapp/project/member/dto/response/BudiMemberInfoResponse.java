package com.yapp.project.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class BudiMemberInfoResponse {
    private final Long id;
    private final String imgUrl;
    private final String nickName;
    private final String description;
    private final String level;
    private final int basePosition;
    private final List<String> positions;
    private final Long likeCount;

    private final List<ProjectResponse> projectList;
    private final String[] portfolioList;

    private final Boolean isLikedFromCurrentMember;
}
