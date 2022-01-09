package com.yapp.project.member.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.member.dto.response.LikeMemberResponse;
import com.yapp.project.member.service.JwtService;
import com.yapp.project.member.service.LikeMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/members")
@Api(tags = "사용자 좋아요(팔로잉)")
@Validated
public class LikeMemberController {
    private final LikeMemberService likeMemberService;
    private final JwtService jwtService;

    @ApiOperation(value = "사용자 좋아요 상태 변경", notes = "toggle된 좋아요 상태(true, false)와 갱신된 좋아요 개수를 응답합니다")
    @PutMapping(value = "/{memberId}/like-members")
    public ResponseEntity<ApiResult> switchLikeMemberStatus(
            @PathVariable @Positive long memberId,
            @RequestHeader("accessToken") @NotBlank String accessToken
    ) {

        jwtService.validateTokenForm(accessToken);

        Long fromMemberId = jwtService.getMemberId(accessToken);
        boolean isLiked = likeMemberService.switchLikeMemberStatus(fromMemberId, memberId);

        var response = new HashMap<String, Boolean>();
        response.put("isLiked", isLiked);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation(value = "내가 좋아한 모든 버디", notes = "'나의 버디 -> 관심목록 -> 버디' 결과입니다")
    @GetMapping(value = "/like-members")
    public ResponseEntity<ApiResult> getAll(
            @RequestHeader("accessToken") @NotBlank String accessToken,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
        jwtService.validateTokenForm(accessToken);

        long fromMemberId = jwtService.getMemberId(accessToken);
        Page<LikeMemberResponse> response = likeMemberService.findAll(pageable, fromMemberId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }
}
