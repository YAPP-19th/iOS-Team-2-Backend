package com.yapp.project.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private long memberId;
    private String userId;
    private String accessToken;

}
