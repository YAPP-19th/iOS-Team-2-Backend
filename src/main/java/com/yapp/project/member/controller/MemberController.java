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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.HashMap;
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
        Long memberId = memberService.createInfo(accessToken, request);

        var response = new HashMap<String, Long>();
        response.put("memberId", memberId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation(value = "버디 찾기", notes = "developer / designer / planner 중 하나로 요청해주세요.")
    @GetMapping(value = "/budiLists/{position}")
    public ResponseEntity<ApiResult> getBudiList(
            @PathVariable @NotBlank String position,
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC, size = 20) Pageable pageable
    ) {

        Page<BudiMemberResponse> response = memberService.getBudiList(pageable, position);
        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation(value = "버디 찾기(필터)", notes = "developer / designer / planner 중 하나로 요청해주세요. \n detailPosition 예시: iOS 개발")
    @GetMapping(value = "/budiLists/{position}/{detailPosition}")
    public ResponseEntity<ApiResult> getBudiList(
            @PathVariable @NotBlank String position,
            @PathVariable @NotBlank String detailPosition,
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC, size = 20) Pageable pageable
    ) {

        Page<BudiMemberResponse> response = memberService.getBudiPositionList(position, detailPosition, pageable);
        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation(value = "버디 상세조회", notes = "member id를 요청해주세요.")
    @GetMapping(value = "/budiDetails/{memberId}")
    public ResponseEntity<ApiResult> getBudiDetail(
            @RequestHeader(value = "accessToken", required = false) @Nullable String accessToken,
            @PathVariable @Positive long memberId
    ) {

        BudiMemberInfoResponse response = memberService.getBudiInfo(memberId, Optional.ofNullable(accessToken));

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

}
