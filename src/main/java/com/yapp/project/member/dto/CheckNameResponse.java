package com.yapp.project.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckNameResponse {
    @ApiModelProperty(name = "name", example = "nickName1")
    boolean available;
}
