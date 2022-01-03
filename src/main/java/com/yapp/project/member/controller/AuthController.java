package com.yapp.project.member.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.member.dto.request.FcmTokenRequest;
import com.yapp.project.member.dto.request.LoginRequest;
import com.yapp.project.member.dto.response.LoginResponse;
import com.yapp.project.member.service.JwtService;
import com.yapp.project.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/auth")
@Api(tags = "인증설정")
public class AuthController {
    private final MemberService memberService;
    private final JwtService jwtService;

    @ApiOperation(value = "로그인", notes = "access Token Return (expiredTime : 1hour)")
    @PostMapping(value = "/login")
    public ResponseEntity<ApiResult> authRequest(@Valid @RequestBody LoginRequest request) {
        Long memberId = null;
        String loginId = memberService.findByLoginId(request.getLoginId());
        LoginResponse response;
        if (loginId.equals(""))
            memberId = memberService.create(request.getLoginId());
        response = jwtService.loginResponse(memberId, request.getLoginId());
        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.LOGIN_SUCCESS, response)
        );
    }

    @ApiOperation(value = "FCM 토큰 저장", notes = "알림 비활성화를 하더라도 fcm 토큰은 서버에 전송")
    @PutMapping(value = "/fcms")
    public ResponseEntity<ApiResult> updateFcmToken(
            @Valid @RequestBody FcmTokenRequest request,
            @RequestHeader("accessToken") @NotBlank String accessToken
    ) {

        jwtService.validateTokenForm(accessToken);
        long currentMemberId = jwtService.getMemberId(accessToken);

        memberService.updateFcmToken(currentMemberId, request.getFcmToken(), request.getIsFcmTokenActive());

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS)
        );
    }
}
