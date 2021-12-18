package com.yapp.project.member.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckNameResponse {
    @ApiModelProperty(name = "name", example = "nickName1")
    private boolean exist;

    public CheckNameResponse(boolean exist) {
        this.exist = exist;
    }
}
