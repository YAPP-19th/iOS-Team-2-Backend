package com.yapp.project.member.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateInfoRequest {
    private String accessToken;

    private String nickName;

    private String memberAddress;

    private String description;

    private int basePosition;

    private List<String> positionList;

    private List<CareerRequest> careerList;

    private List<ProjectRequest> projectList;

}
