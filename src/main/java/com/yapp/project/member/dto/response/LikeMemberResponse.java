package com.yapp.project.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeMemberResponse {
    private List<LikedMember> likedMembers;

    @Getter
    @RequiredArgsConstructor
    public static class LikedMember{
        private final Long memberId;

        private final String nickName;

        private final String profileImageUrl;

        private final String address;

        private final String position;
    }
}