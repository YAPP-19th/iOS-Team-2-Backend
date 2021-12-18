package com.yapp.project.member.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.member.dto.response.LikeMemberResponse;
import com.yapp.project.member.service.JwtService;
import com.yapp.project.member.service.LikeMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/members")
@Api(tags = "사용자 좋아요(팔로잉)")
public class LikeMemberController {
    private final LikeMemberService likeMemberService;
    private final JwtService jwtService;

    @ApiOperation("사용자 좋아요 상태 변경")
    @PutMapping(value = "/{memberId}/like-members")
    public ResponseEntity<ApiResult> switchLikeMemberStatus(@PathVariable Long memberId, @RequestHeader("accessToken") String accessToken) {
        jwtService.validateTokenForm(accessToken);

        Long fromMemberId = jwtService.getMemberId(accessToken);
        likeMemberService.switchLikeMemberStatus(fromMemberId, memberId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS)
        );
    }

    @ApiOperation("내가 좋아한 모든 상대방")
    @GetMapping(value = "/like-members")
    public ResponseEntity<ApiResult> getAll(@RequestHeader("accessToken") String accessToken) {
        jwtService.validateTokenForm(accessToken);

        Long fromMemberId = jwtService.getMemberId(accessToken);
        LikeMemberResponse response = likeMemberService.findAll(fromMemberId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }
}
