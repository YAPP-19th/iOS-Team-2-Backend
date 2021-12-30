package com.yapp.project.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class CareerRequest {  //TODO: 삭제
    private Long memberId;

    private String companyName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate endDate;

    private String description;

    private Boolean nowWorks;

    private String teamName;

    private List<ProjectRequest> workRequestList;

}
