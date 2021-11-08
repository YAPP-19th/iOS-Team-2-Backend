package com.yapp.project.post.service;

import com.yapp.project.common.vo.Position;
import com.yapp.project.common.vo.Skill;
import com.yapp.project.post.dto.response.RecruitingStatusResponse;
import com.yapp.project.post.entity.RecruitingPosition;
import org.springframework.stereotype.Component;

@Component
public class RecruitingPositionConverter {
    public RecruitingPosition toRecruitingPositionEntity(int positionCode, int skillCode, int recruitingNumber){
        return RecruitingPosition.builder()
                .positionCode(Position.of(positionCode).getPositionCode())
                .skillCode(Skill.of(skillCode).getSkillCode())
                .recruitingNumber(recruitingNumber)
                .build();
    }

    public RecruitingStatusResponse toRecruitingStatus(int positionCode, int skillCode, String status) {
        return new RecruitingStatusResponse(
                positionCode,
                skillCode,
                status
        );
    }
}
