package com.yapp.project.member.service;

import com.yapp.project.common.value.Position;
import com.yapp.project.member.dto.request.CareerRequest;
import com.yapp.project.member.dto.response.CheckNameResponse;
import com.yapp.project.member.dto.request.CreateInfoRequest;
import com.yapp.project.member.dto.request.ProjectRequest;
import com.yapp.project.member.entity.Career;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.entity.Project;
import com.yapp.project.member.entity.Work;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MemberConverter {

    public Member toCreateMember(String loginId){
        return Member.builder()
                .loginId(loginId)
                .build();
    }

    public CheckNameResponse toCheckNameResponse(boolean available) {
        return CheckNameResponse.builder()
                .available(!available)
                .build();
    }

    public Member toSignUpResponseMember(Optional<Member> member, CreateInfoRequest request) {
        List<String> positionList = request.getPositionList()
                                            .stream()
                                            .map(v->Position.of(v).getPositionCode())
                                            .map(v->v.toString())
                                            .collect(Collectors.toList());
        String positionListString = StringUtils.join(positionList,'|');
        return Member.builder()
                .id(member.get().getId())
                .token(member.get().getToken())
                .score(member.get().getScore())
                .email(member.get().getEmail())
                .loginId(member.get().getLoginId())
                .profileImageUrl(member.get().getProfileImageUrl())
                .nickName(request.getNickName())
                .address(request.getMemberAddress())
                .introduce(request.getDescription())
                .basePositionCode(request.getBasePosition())
                .positionCode(positionListString)
                .build();
    }

    public Project toSignUpResponseProject(Member m, ProjectRequest request) {
            return Project.builder()
                    .name(request.getName())
                    .startDate(request.getStartDate())
                    .endDate(request.getEndDate())
                    .description(request.getDescription())
                    .member(m)
                    .build();

    }

    public Career toSignUpResponseCareer(Member m, CareerRequest request) {
            return Career.builder()
                    .companyName(request.getCompanyName())
                    .startDate(request.getStartDate())
                    .endDate(request.getEndDate())
                    .nowWorks(request.getNowWorks())
                    .teamName(request.getTeamName())
//                    .works(toSignUpResponseWork(request.getWorkRequestList()))
                    .member(m)
                    .build();
    }

    public Work toSignUpResponseWork(Career c, ProjectRequest request) {
            return Work.builder()
                    .name(request.getName())
                    .startDate(request.getStartDate())
                    .endDate(request.getEndDate())
                    .description(request.getDescription())
                    .career(c)
                    .build();
    }
}
