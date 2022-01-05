package com.yapp.project.member.dto.request;

import com.yapp.project.common.exception.DtoValidationFailMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FcmTokenRequest {
    @NotBlank(message = DtoValidationFailMessage.INVALID_FCM_TOKEN_REQUEST)
    private String fcmToken;
}
