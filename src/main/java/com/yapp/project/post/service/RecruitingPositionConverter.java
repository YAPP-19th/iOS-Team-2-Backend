package com.yapp.project.post.service;

import com.yapp.project.common.value.Position;
import com.yapp.project.post.dto.response.PositionAndColor;
import com.yapp.project.post.dto.response.RecruitingStatusResponse;
import com.yapp.project.post.entity.RecruitingPosition;
import org.springframework.stereotype.Component;

@Component
public class RecruitingPositionConverter {
    public RecruitingPosition toRecruitingPositionEntity(String positionName, int recruitingNumber){
        return RecruitingPosition.builder()
                .basePositionCode(Position.getBasePosition(Position.of(positionName).getCode()).getCode())
                .positionCode(Position.of(positionName).getCode())
                .recruitingNumber(recruitingNumber)
                .build();
    }

    public RecruitingStatusResponse.RecruitingStatus toRecruitingStatus(RecruitingPosition recruitingPosition, long approvedCount) {
        return new RecruitingStatusResponse.RecruitingStatus(
                recruitingPosition.getId(),
                new PositionAndColor(
                        Position.of(recruitingPosition.getPositionCode()).getName(),
                        Position.getBasePosition(recruitingPosition.getPositionCode()).getCode()
                ),
                recruitingPosition.getRecruitingNumber(),
                approvedCount + "/" + recruitingPosition.getRecruitingNumber()
        );
    }
}
