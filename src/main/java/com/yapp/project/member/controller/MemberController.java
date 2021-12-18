package com.yapp.project.member.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
<<<<<<< HEAD
import com.yapp.project.member.dto.response.BudiMemberInfoResponse;
=======
>>>>>>> 35fe70eae9e2493a61f1676738d7245c3682f1c0
import com.yapp.project.member.dto.response.BudiMemberResponse;
import com.yapp.project.member.dto.response.CheckNameResponse;
import com.yapp.project.member.dto.request.CreateInfoRequest;
import com.yapp.project.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResult> createInfo(@RequestHeader("accessToken") String accessToken, @RequestBody CreateInfoRequest request) {
        Long response = memberService.createInfo(accessToken, request);
        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation(value = "버디 찾기", notes = "developer / designer / planner 중 하나로 요청해주세요.")
    @GetMapping(value = "/getBudiList/{position}")
    public ResponseEntity<ApiResult> getBudiList(@PathVariable String position) {
        List<BudiMemberResponse> response = memberService.getBudiList(position);
        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation(value = "버디 상세조회", notes = "member id를 요청해주세요.")
    @GetMapping(value = "/getBudiDetail/{id}")
    public ResponseEntity<ApiResult> getBudiDetail(@RequestHeader("accessToken") String accessToken, @PathVariable Long id) {
        BudiMemberInfoResponse response = memberService.getBudiInfo(id);
        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }
}
