package com.yapp.project.apply.service;

import com.yapp.project.apply.entity.Apply;
import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.RecruitingPosition;
import org.springframework.stereotype.Component;

@Component
public class ApplyConverter {
    public Apply toEntity(Member member, RecruitingPosition recruitingPosition, int applyStatusCode){
        return Apply.builder()
                .member(member)
                .recruitingPosition(recruitingPosition)
                .applyStatusCode(applyStatusCode)
                .post(recruitingPosition.getPost())
                .build();
    }
}
