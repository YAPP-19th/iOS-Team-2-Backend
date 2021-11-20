package com.yapp.project.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
@Getter
public class LoginRequest {
    @ApiModelProperty(notes = "기존 발급받은 access Token 존재 시 전달", example = "")
    private String accessToken;
    @ApiModelProperty(name = "identifier", notes = "소셜로그인에서 받은 identifier 값 전달", example = "17-76025468", required = true)
    private String loginId;
    @ApiModelProperty(name = "email", notes = "소셜 로그인에서 제공받은 값 존재시 전달", example = "test@naver.com")
    private String email;
    @ApiModelProperty(name = "name", notes = "소셜 로그인에서 제공받은 값 존재시 전달", example = "별명")
    private String name;

    @Builder
    public LoginRequest(String accessToken, String loginId, String email, String nickName){
        this.accessToken = accessToken;
        this.loginId = loginId;
        this.email = email;
        this.name = nickName;
    }

}

