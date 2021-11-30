package com.yapp.project.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class LikeMemberResponse {
    List<LikedMember> likedMembers;

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