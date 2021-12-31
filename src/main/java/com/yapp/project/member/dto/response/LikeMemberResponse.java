package com.yapp.project.member.dto.response;

import com.yapp.project.common.dto.PositionAndColor;
import lombok.*;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class LikeMemberResponse {
    private final Long memberId;

    private final String nickName;

    private final String profileImageUrl;

    private final String address;

    private final PositionAndColor position;

    private final Long likeCount;
}