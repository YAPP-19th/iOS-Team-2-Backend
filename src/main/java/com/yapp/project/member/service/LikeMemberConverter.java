package com.yapp.project.member.service;

import com.yapp.project.common.dto.PositionAndColor;
import com.yapp.project.common.value.Position;
import com.yapp.project.member.dto.response.LikeMemberResponse;
import com.yapp.project.member.entity.LikeMember;
import com.yapp.project.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class LikeMemberConverter {
    public LikeMemberResponse toLikeMemberResponse(LikeMember likeMember) {
        Member toMember = likeMember.getToMember();

        return LikeMemberResponse.builder()
                .memberId(toMember.getId())
                .nickName(toMember.getNickName())
                .profileImageUrl(toMember.getProfileImageUrl())
                .address(toMember.getAddress())
                .position(
                        new PositionAndColor(  //TODO: 멤버 포지션을 " "로 나눌지 결정
                                Position.of(Integer.parseInt(toMember.getPositionCode().split(" ")[0])).getName(),
                                Position.getBasePosition(Integer.parseInt(toMember.getPositionCode().split(" ")[0])).getCode()
                        )
                )
                .likeCount(toMember.getLikeCount())
                .build();
    }
}
