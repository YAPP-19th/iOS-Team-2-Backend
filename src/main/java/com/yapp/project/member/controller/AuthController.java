package com.yapp.project.member.controller;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.member.dto.LoginRequest;
import com.yapp.project.member.dto.LoginResponse;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.service.JwtService;
import com.yapp.project.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/auth")
@Api(tags = "Login (Project)")
public class AuthController {
    private final MemberService memberService;
    private final JwtService jwtService;
    @ApiOperation(value = "로그인", notes = "첫 로그인시(회원가입),accessToken 공백('') / 이후 로그인 시 accessToken 필 / 유효기간 만료 시 MEMBER_NOT_FOUND")
    @PostMapping(value = "/login")
    public ResponseEntity<ApiResult> authRequest(@Valid @RequestBody LoginRequest request) {
        String memberId = memberService.findByLoginId(request.getLoginId());
        LoginResponse response;
        if (!memberId.equals("")) {
            if(request.getAccessToken().isBlank()){
                throw new NotFoundException(ExceptionMessage.DATA_BINDING_FAIL);
            }
            else if (jwtService.validate(request.getLoginId(), request.getAccessToken()).isValidation()) {
                response = jwtService.loginResponse(memberId);
                EntityModel<LoginResponse> entityModel = EntityModel.of(
                        response
                );
                return ResponseEntity.ok(
                        ApiResult.of(ResponseMessage.LOGIN_SUCCESS, entityModel)
                );
            }
        }
        else if(request.getAccessToken().isEmpty()){
            memberService.create(request.getLoginId(), request.getEmail());
            response = jwtService.loginResponse(request.getLoginId());
            EntityModel<LoginResponse> entityModel = EntityModel.of(
                    response
            );
            return ResponseEntity.ok(
                    ApiResult.of(ResponseMessage.SUCCESS_SIGN_UP, entityModel)
            );
        }
        throw new NotFoundException(ExceptionMessage.MEMBER_NOT_FOUND);
    }
}
