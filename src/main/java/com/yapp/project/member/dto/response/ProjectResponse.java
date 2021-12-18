package com.yapp.project.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ProjectResponse {
    Long projectId;
    String name;
    LocalDate startDate;
    LocalDate endDate;
    String description;
}
