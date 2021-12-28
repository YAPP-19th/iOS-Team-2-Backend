package com.yapp.project.member.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.member.dto.response.BudiMemberInfoResponse;
import com.yapp.project.member.dto.response.BudiMemberResponse;
import com.yapp.project.member.dto.response.CheckNameResponse;
import com.yapp.project.member.dto.request.CreateInfoRequest;
import com.yapp.project.member.service.JwtService;
import com.yapp.project.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/members")
@Api(tags = "사용자 관련")
@Validated
public class MemberController {
    private final MemberService memberService;

    private final JwtService jwtService;

    @ApiOperation(value = "닉네임 중복 확인", notes = "중복이면 false 반환 / 중복 아니면 true 반환")
    @GetMapping(value = "/checkDuplicateName")
    public ResponseEntity<ApiResult> checkDuplicateName(@RequestParam("name") @NotBlank String name) {
        CheckNameResponse response = memberService.checkDuplicateName(name);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("회원 정보 입력")
    @PostMapping(value = "/infos")
    public ResponseEntity<ApiResult> createInfo(@RequestHeader("accessToken") @NotBlank String accessToken, @Valid @RequestBody CreateInfoRequest request) {
        jwtService.validateTokenForm(accessToken);
        Long response = memberService.createInfo(accessToken, request);
        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation(value = "버디 찾기", notes = "developer / designer / planner 중 하나로 요청해주세요.")
    @GetMapping(value = "/budiLists/{position}")
    public ResponseEntity<ApiResult> getBudiList(@PathVariable @NotBlank String position) {
        List<BudiMemberResponse> response = memberService.getBudiList(position);
        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation(value = "버디 상세조회", notes = "member id를 요청해주세요.")
    @GetMapping(value = "/budiDetails/{memberId}")
    public ResponseEntity<ApiResult> getBudiDetail(
            @RequestHeader("accessToken") @Nullable String accessToken,
            @PathVariable @Positive Long memberId
    ) {

        BudiMemberInfoResponse response = memberService.getBudiInfo(memberId, Optional.ofNullable(accessToken));

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

}
