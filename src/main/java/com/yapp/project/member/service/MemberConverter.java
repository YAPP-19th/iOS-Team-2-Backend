package com.yapp.project.member.service;

import com.yapp.project.common.util.PositionParser;
import com.yapp.project.common.value.Level;
import com.yapp.project.common.value.Position;
import com.yapp.project.common.value.BasePosition;
import com.yapp.project.member.dto.response.BudiMemberInfoResponse;
import com.yapp.project.member.dto.response.BudiMemberResponse;
import com.yapp.project.member.dto.response.CheckNameResponse;
import com.yapp.project.member.dto.request.CreateInfoRequest;
import com.yapp.project.member.dto.request.ProjectRequest;
import com.yapp.project.member.dto.response.ProjectResponse;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.entity.Project;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MemberConverter {

    public Member toCreateMember(String loginId) {
        return Member.builder()
                .loginId(loginId)
                .likeCount(0L)
                .score(0)
                .address("")  //TODO: 초기 값으로 설정 해주지 않으면 오류 발생
                .basePositionCode(BasePosition.DEVELOPER.getCode())
                .email("")
                .introduce("")
                .nickName("")
                .positionCode("--") //TODO: position code가 string이어서 다른 부분에서 에러발생
                .portfolioLink("")
                .projects(List.of())
                .profileImageUrl("")
                .isFcmTokenActive(true)
                .build();
    }

    public CheckNameResponse toCheckNameResponse(boolean available) {
        return new CheckNameResponse(available);
    }

    public Member toMemberEntity(Member member, CreateInfoRequest request, int score) {
        List<String> positionList = request.getPositionList()
                .stream()
                .map(v -> Position.of(v).getCode())
                .map(v -> v.toString())
                .collect(Collectors.toList());
        String joinedPositions = PositionParser.join(positionList, "-");
        return Member.builder()
                .id(member.getId())
                .token(member.getToken())
                .score(score)
                .email(member.getEmail())
                .loginId(member.getLoginId())
                .profileImageUrl(member.getProfileImageUrl())
                .nickName(request.getNickName())
                .address(request.getMemberAddress())
                .introduce(request.getDescription())
                .basePositionCode(request.getBasePosition())
                .positionCode(joinedPositions)
                .portfolioLink(request.getPortfolioLink()
                        .stream()
                        .map(n -> String.valueOf(n))
                        .collect(Collectors.joining(" ")))
                .likeCount(0L)
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

    public BudiMemberResponse toBudiMemberResponse(Member member) {
        List<String> positionNames = new LinkedList<>();
        int[] codes = PositionParser.parse(member.getPositionCode(), "-");
        for (int code : codes) {
            positionNames.add(Position.of(code).getName());
        }

        return new BudiMemberResponse(
                member.getId(),
                member.getProfileImageUrl(),
                member.getNickName(),
                member.getAddress(),
                member.getIntroduce(),
                positionNames,
                member.getLikeCount()
        );
    }

    public BudiMemberInfoResponse toBudiMemberInfoResponse(Member m, List<Project> projectList, boolean isLikedFromCurrentMember) {
        List<String> positionNames = new LinkedList<>();
        int[] codes = PositionParser.parse(m.getPositionCode(), "-");
        String[] portfolioList = m.getPortfolioLink().split(" ");
        for (int code : codes) {
            positionNames.add(Position.of(code).getName());
        }
        List<ProjectResponse> projectResponses = toProjectResponse(projectList);
        BudiMemberInfoResponse response = BudiMemberInfoResponse.builder()
                .id(m.getId())
                .imgUrl(m.getProfileImageUrl())
                .nickName(m.getNickName())
                .description(m.getIntroduce())
                .level(Level.of(m.getScore()).getLevelName())
                .positions(positionNames)
                .projectList(projectResponses)
                .portfolioList(portfolioList)
                .likeCount(m.getLikeCount())
                .isLikedFromCurrentMember(isLikedFromCurrentMember)
                .build();
        return response;
    }

    public List<ProjectResponse> toProjectResponse(List<Project> projects) {
        List<ProjectResponse> projectList = new LinkedList<>();
        for (Project project : projects) {
            projectList.add(new ProjectResponse(project.getId(), project.getName(), project.getStartDate(), project.getEndDate(), project.getDescription()));
        }
        return projectList;
    }
}
