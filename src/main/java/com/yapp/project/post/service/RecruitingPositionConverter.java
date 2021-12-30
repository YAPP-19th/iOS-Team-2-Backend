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

    public RecruitingStatusResponse.RecruitingStatus toRecruitingStatus(Long recruitingPositionId, int positionCode, String status) {
        return new RecruitingStatusResponse.RecruitingStatus(
                recruitingPositionId,
                new PositionAndColor(
                        Position.of(positionCode).getName(),
                        Position.getBasePosition(positionCode).getCode()
                ),
                status
        );
    }
}
