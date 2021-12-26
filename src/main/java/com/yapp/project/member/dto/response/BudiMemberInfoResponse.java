package com.yapp.project.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BudiMemberInfoResponse {
    private Long id;
    private String imgUrl;
    private String nickName;
    private String level;
    private List<String> position;

    private List<ProjectResponse> projectList;
    private String[] portfolioList;

    // TODO: like member
}
