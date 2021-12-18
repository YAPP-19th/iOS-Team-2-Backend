package com.yapp.project.member.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Builder
public class ProjectRequest {
    String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDate endDate;

    String description;

}
