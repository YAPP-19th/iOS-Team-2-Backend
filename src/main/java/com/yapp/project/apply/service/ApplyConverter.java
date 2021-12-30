package com.yapp.project.apply.service;

import com.yapp.project.apply.dto.response.ApplicantResponse;
import com.yapp.project.apply.dto.response.ApplyResponse;
import com.yapp.project.apply.entity.Apply;
import com.yapp.project.apply.entity.value.ApplyStatus;
import com.yapp.project.common.value.Position;
import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.RecruitingPosition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApplyConverter {
    public Apply toEntity(Member member, RecruitingPosition recruitingPosition, int applyStatusCode) {
        return Apply.builder()
                .member(member)
                .recruitingPosition(recruitingPosition)
                .applyStatusCode(applyStatusCode)
                .post(recruitingPosition.getPost())
                .build();
    }

    public ApplyResponse toApplyResponse(Apply applyEntity) {
        return new ApplyResponse(
                applyEntity.getId(),
                ApplyStatus.of(applyEntity.getApplyStatusCode()).getApplyStatusName(),
                applyEntity.getRecruitingPosition().getId(),
                applyEntity.getPost().getId()
        );
    }

    public List<ApplicantResponse> toApplicantResponses(List<Apply> applies) {
        List<ApplicantResponse> result = new ArrayList<>();

        for (var apply : applies) {
            result.add(
                    ApplicantResponse.builder()
                            .applyId(apply.getId())
                            .applyer(
                                    ApplicantResponse.ApplyerResponse.builder()
                                            .id(apply.getMember().getId())
                                            .profileImageUrl((apply.getMember().getProfileImageUrl()))
                                            .nickName(apply.getMember().getNickName())
                                            .address(apply.getMember().getAddress())
                                            .position(
                                                    new ApplicantResponse.PositionAndColor(
                                                            Position.of(apply.getMember().getBasePositionCode()).getPositionName(),
                                                            Position.getBasePosition(apply.getMember().getBasePositionCode()).getCode()
                                                    )
                                            )
                                            .isApproved(ApplyStatus.isApproved(apply.getApplyStatusCode().intValue()))
                                            .build()
                            )
                            .recruitingPositionResponse(
                                    new ApplicantResponse.RecruitingPositionResponse(
                                            apply.getRecruitingPosition().getId(),
                                            Position.of(apply.getRecruitingPosition().getBasePositionCode()).getPositionName()
                                    )
                            )
                            .build()
            );
        }

        return result;
    }
}
