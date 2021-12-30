package com.yapp.project.post.dto.response;

import com.yapp.project.common.dto.PositionAndColor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class TeamMemberResponse {
    private final List<TeamMember> teamMembers = new ArrayList<>();

    @Getter
    @RequiredArgsConstructor
    public static class TeamMember{
        private final Long memberId;

        private final String nickName;

        private final String profileImageUrl;

        private final String address;

        private final PositionAndColor position;
    }
}
