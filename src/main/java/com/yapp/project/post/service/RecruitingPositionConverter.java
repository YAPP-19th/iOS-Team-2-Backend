package com.yapp.project.post.service;

import com.yapp.project.common.value.Position;
import com.yapp.project.common.value.Skill;
import com.yapp.project.post.dto.response.RecruitingStatusResponse;
import com.yapp.project.post.entity.RecruitingPosition;
import org.springframework.stereotype.Component;

@Component
public class RecruitingPositionConverter {
    public RecruitingPosition toRecruitingPositionEntity(String positionName, String skillName, int recruitingNumber){
        return RecruitingPosition.builder()
                .positionCode(Position.of(positionName).getPositionCode())
                .skillCode(Skill.of(skillName).getSkillCode())
                .recruitingNumber(recruitingNumber)
                .build();
    }

    public RecruitingStatusResponse toRecruitingStatus(Long recruitingPositionId, int positionCode, int skillCode, String status) {
        return new RecruitingStatusResponse(
                recruitingPositionId,
                Position.of(positionCode).getPositionName(),
                Position.of(positionCode).getPositionCode(),
                Skill.of(skillCode).getSkillName(),
                Skill.of(skillCode).getSkillCode(),
                status
        );
    }
}
