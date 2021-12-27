package com.yapp.project.apply.dto.request;

import com.yapp.project.common.exception.DtoValidationFailMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;

@Getter
@AllArgsConstructor
public class ApplyRequest {
    @ApiModelProperty(example = "1")
    @Min(value = 1L, message = DtoValidationFailMessage.INVALID_POST_ID)
    private Long postId;

    @ApiModelProperty(example = "1")
    @Min(value = 1L, message = DtoValidationFailMessage.INVALID_RECRUITINGPOSITION_ID)
    private Long recruitingPositionId;
}
