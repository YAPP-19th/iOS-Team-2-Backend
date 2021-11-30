package com.yapp.project.post.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TeamMemberResponse {
    private final Long leaderId;

    private final String nickName;

    private final String profileImageUrl;

    private final String address;
}
