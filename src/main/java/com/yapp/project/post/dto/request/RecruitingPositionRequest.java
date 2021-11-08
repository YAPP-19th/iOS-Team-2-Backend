package com.yapp.project.post.dto.request;

import com.yapp.project.common.exception.DtoValidationFailMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;

@Getter
@AllArgsConstructor
public class RecruitingPositionRequest {
    private int positionCode;

    private int skillCode;

    @Min(value = 1, message = DtoValidationFailMessage.INVALID_RECRUITING_NUMBER_NUMBER)
    private int recruitingNumber;
}
