package com.yapp.project.member.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class LoginRequest {
    @ApiModelProperty(name = "identifier", notes = "소셜로그인에서 받은 identifier 값 전달", example = "17-76025468", required = true)
    @NotNull
    private String loginId;

    public LoginRequest(String loginId){
        this.loginId = getLoginId();
    }
}