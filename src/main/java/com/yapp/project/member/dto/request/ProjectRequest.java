package com.yapp.project.member.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ProjectRequest {
    String name;

    LocalDate startDate;

    LocalDate endDate;

    String description;

}
