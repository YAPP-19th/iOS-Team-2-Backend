package com.yapp.project.post.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class TeamMemberResponse {
    List<TeamMember> teamMembers;

    @Getter
    @RequiredArgsConstructor
    public static class TeamMember{
        private final Long memberId;

        private final String nickName;

        private final String profileImageUrl;

        private final String address;
    }
}
