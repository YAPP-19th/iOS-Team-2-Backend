package com.yapp.project.member.controller;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.member.dto.LoginRequest;
import com.yapp.project.member.dto.LoginResponse;
import com.yapp.project.member.service.JwtService;
import com.yapp.project.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/auth")
@Api(tags = "Login")
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
}
