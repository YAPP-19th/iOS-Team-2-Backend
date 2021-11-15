package com.yapp.project.post.dto.request;

import com.yapp.project.common.exception.DtoValidationFailMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class RecruitingPositionRequest {
    @NotBlank(message = DtoValidationFailMessage.INVALID_POSITION_NAME)
    private String positionName;

    @NotBlank(message = DtoValidationFailMessage.INVALID_SKILL_NAME)
    private String skillName;

    @Min(value = 1, message = DtoValidationFailMessage.INVALID_RECRUITING_NUMBER_NUMBER)
    private int recruitingNumber;
}
