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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "선택하는 리뷰")
public class CodeReviewHistoryController {
    private final CodeReviewHistoryService codeReviewHistoryService;
    private final JwtService jwtService;

    @ApiOperation("리뷰 '목록' 전체 조회")
    @GetMapping(value = "/select-reviews-list")
    public ResponseEntity<ApiResult> getAllSelectReviewList() {
        var response = codeReviewHistoryService.findAllReviews();

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("특정 사용자 프로필에 '선택하는 리뷰' 등록")
    @PostMapping(value = "/select-reviews")
    public ResponseEntity<ApiResult> insert(
            @RequestHeader("accessToken") String accessToken,
            @RequestParam(required = true) long memberId,
            @RequestParam(required = true) long postId,
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
    public ResponseEntity<ApiResult> getAll(@RequestParam(required = true) long memberId) {
        CodeReviewCountResponse response = codeReviewHistoryService.findAllByMember(memberId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }
}
