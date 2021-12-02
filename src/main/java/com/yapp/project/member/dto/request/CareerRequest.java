package com.yapp.project.member.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class CareerRequest {
    private Long memberId;

    private String companyName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    private Boolean nowWorks;

    private String teamName;

    private List<ProjectRequest> workRequestList;

}
