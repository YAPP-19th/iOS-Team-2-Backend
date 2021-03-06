package com.yapp.project.member.dto.request;

import com.yapp.project.common.exception.DtoValidationFailMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {
    @NotBlank(message = DtoValidationFailMessage.INVALID_PROJECT_TITLE)
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate endDate;

    @NotBlank(message = DtoValidationFailMessage.INVALID_DESCRIPTION)
    private String description;

}
