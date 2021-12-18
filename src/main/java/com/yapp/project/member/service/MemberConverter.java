package com.yapp.project.member.service;

import com.yapp.project.common.value.Position;
import com.yapp.project.member.dto.request.CareerRequest;
import com.yapp.project.member.dto.response.BudiMemberResponse;
import com.yapp.project.member.dto.response.CheckNameResponse;
import com.yapp.project.member.dto.request.CreateInfoRequest;
import com.yapp.project.member.dto.request.ProjectRequest;
import com.yapp.project.member.entity.Career;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.entity.Project;
import com.yapp.project.member.entity.Work;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MemberConverter {

    public Member toCreateMember(String loginId){
        return Member.builder()
                .loginId(loginId)
                .build();
    }

    public CheckNameResponse toCheckNameResponse(boolean available) {
        return new CheckNameResponse(available);
    }

    public Member toMemberEntity(Optional<Member> member, CreateInfoRequest request, int score) {
        List<String> positionList = request.getPositionList()
                                            .stream()
                                            .map(v->Position.of(v).getPositionCode())
                                            .map(v->v.toString())
                                            .collect(Collectors.toList());
        String positionListString = StringUtils.join(positionList,' ');
        return Member.builder()
                .id(member.get().getId())
                .token(member.get().getToken())
                .score(score)
                .email(member.get().getEmail())
                .loginId(member.get().getLoginId())
                .profileImageUrl(member.get().getProfileImageUrl())
                .nickName(request.getNickName())
                .address(request.getMemberAddress())
                .introduce(request.getDescription())
                .basePositionCode(request.getBasePosition())
                .positionCode(positionListString)
                .portfolioLink(request.getPortfolioLink()
                        .stream()
                        .map(n-> String.valueOf(n))
                        .collect(Collectors.joining(" ")))
                .build();
    }

    public Project toProjectEntity(Member m, ProjectRequest request) {
            return Project.builder()
                    .name(request.getName())
                    .startDate(request.getStartDate())
                    .endDate(request.getEndDate())
                    .description(request.getDescription())
                    .member(m)
                    .build();

    }

    public Career toCareerEntity(Member m, CareerRequest request) {
            return Career.builder()
                    .companyName(request.getCompanyName())
                    .startDate(request.getStartDate())
                    .endDate(request.getEndDate())
                    .nowWorks(request.getNowWorks())
                    .teamName(request.getTeamName())
                    .member(m)
                    .build();
    }

    public Work toWorkEntity(Career c, ProjectRequest request) {
            return Work.builder()
                    .name(request.getName())
                    .startDate(request.getStartDate())
                    .endDate(request.getEndDate())
                    .description(request.getDescription())
                    .career(c)
                    .build();
    }

    public List<BudiMemberResponse> toBudiMemberResponse(List<Member> members) {
        List<BudiMemberResponse> responses = new LinkedList<>();
        for(Member m : members){
            List<String> positionList = new LinkedList<>();
            String[] codeList = m.getPositionCode().split(" ");
            for(String code : codeList){
                positionList.add(Position.of(Integer.parseInt(code)).getPositionName());
            }
            responses.add(new BudiMemberResponse(m.getId(), m.getProfileImageUrl(), m.getNickName(), m.getAddress(), m.getIntroduce(), positionList));
        }
        return responses;
    }
}
