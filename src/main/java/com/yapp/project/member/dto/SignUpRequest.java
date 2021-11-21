package com.yapp.project.member.dto;

import com.yapp.project.common.value.RootPosition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpRequest {
    @ApiModelProperty(name = "name", example = "nickName1")
    String nickName;

    String memberAddress;

    RootPosition rootPosition;




}
