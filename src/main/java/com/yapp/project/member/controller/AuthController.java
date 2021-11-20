package com.yapp.project.member.controller;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.member.dto.LoginRequest;
import com.yapp.project.member.dto.TokenResponse;
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
    @ApiOperation(value = "로그인", notes = "login 정보를 받고 jwt 토큰 반환 / 유효기간 만료 시 MEMBER_NOT_FOUND")
    @PostMapping(value = "/login")
    public ResponseEntity<ApiResult> authRequest(@Valid @ModelAttribute LoginRequest request) {
        Optional<Member> member = memberService.findByLoginId(request.getLoginId());
        TokenResponse response;
        if (!member.isEmpty()) {
            if(request.getAccessToken().isBlank()){
                throw new NotFoundException(ExceptionMessage.DATA_BINDING_FAIL);
            }
            else if (jwtService.validate(request.getLoginId(), request.getAccessToken()).isValidation()) {
                response = jwtService.loginResponse(member.get().getLoginId());
                EntityModel<TokenResponse> entityModel = EntityModel.of(
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
            EntityModel<TokenResponse> entityModel = EntityModel.of(
                    response
            );
            return ResponseEntity.ok(
                    ApiResult.of(ResponseMessage.SUCCESS_SIGN_UP, entityModel)
            );
        }
        throw new NotFoundException(ExceptionMessage.MEMBER_NOT_FOUND);
    }
}
