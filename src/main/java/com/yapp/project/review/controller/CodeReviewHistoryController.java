package com.yapp.project.review.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.member.service.JwtService;
import com.yapp.project.review.dto.request.CodeReviewInsertRequest;
import com.yapp.project.review.dto.response.CodeReviewCountResponse;
import com.yapp.project.review.service.CodeReviewHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "선택하는 리뷰")
@Validated
public class CodeReviewHistoryController {
    private final CodeReviewHistoryService codeReviewHistoryService;
    private final JwtService jwtService;

    @ApiOperation("특정 사용자 프로필에 '선택하는 리뷰' 등록")
    @PostMapping(value = "/select-reviews")
    public ResponseEntity<ApiResult> insert(
            @RequestHeader("accessToken") @NotBlank String accessToken,
            @RequestParam(required = true) @Positive long memberId,
            @RequestParam(required = true) @Positive long postId,
            @Valid @RequestBody CodeReviewInsertRequest request
    ) {

        jwtService.validateTokenForm(accessToken);

        long reviewerId = jwtService.getMemberId(accessToken);

        codeReviewHistoryService.create(reviewerId, memberId, postId, request.getSelectedReviews());

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS)
        );
    }

    @ApiOperation("특정 사용자가 받은 '선택하는 리뷰' 개수")
    @GetMapping(value = "/select-reviews")
    public ResponseEntity<ApiResult> getAll(@RequestParam(required = true) @Positive long memberId) {
        CodeReviewCountResponse response = codeReviewHistoryService.findAllByMember(memberId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation(value = "'특정 프로젝트에서' 내가 받은 '선택하는 리뷰' 개수", notes = "'나의버디 -> 참여프로젝트 -> 완료 -> 내 평가보기' 결과입니다")
    @GetMapping(value = "/select-reviews/posts/{postId}")
    public ResponseEntity<ApiResult> getAllByPost(
            @PathVariable(required = true) @Positive long postId,
            @RequestHeader("accessToken") @NotBlank String accessToken
    ) {

        jwtService.validateTokenForm(accessToken);

        long currentMemberId = jwtService.getMemberId(accessToken);

        CodeReviewCountResponse response = codeReviewHistoryService.findAllByMemberAndPost(currentMemberId, postId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }
}
