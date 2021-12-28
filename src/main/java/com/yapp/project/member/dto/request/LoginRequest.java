package com.yapp.project.member.dto.request;

import com.yapp.project.common.exception.DtoValidationFailMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class LoginRequest {
    @ApiModelProperty(name = "identifier", notes = "소셜로그인에서 받은 identifier 값 전달", example = "12345678", required = true)
    @NotBlank(message = DtoValidationFailMessage.INVALID_LOGIN_ID)
    private String loginId;

    public LoginRequest(String loginId){
        this.loginId = getLoginId();
    }
}