package com.yapp.project.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class LoginRequest {
    @ApiModelProperty(name = "identifier", notes = "소셜로그인에서 받은 identifier 값 전달", example = "17-76025468", required = true)
    @NotNull
    private String loginId;

    @Builder
    public LoginRequest(String loginId){
        this.loginId = loginId;
    }

}

