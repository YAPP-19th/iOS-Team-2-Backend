package com.yapp.project.member.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.member.dto.response.CheckNameResponse;
import com.yapp.project.member.dto.request.CreateInfoRequest;
import com.yapp.project.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/members")
@Api(tags = "Member")
public class MemberController {
    private final MemberService memberService;

    @ApiOperation(value = "닉네임 중복 확인", notes = "중복이면 false 반환 / 중복 아니면 true 반환")
    @GetMapping(value = "/checkDuplicateName")
    public ResponseEntity<ApiResult> checkDuplicateName(@RequestParam("name") String name) {
        CheckNameResponse response = memberService.checkDuplicateName(name);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("회원 정보 입력")
    @PostMapping(value = "/createInfo")
    public ResponseEntity<ApiResult> createInfo(@RequestBody CreateInfoRequest request) {
        Long response = memberService.createInfo(request);
        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }
}
