package com.yapp.project.review.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.member.service.JwtService;
import com.yapp.project.review.dto.request.TextReviewCreateRequest;
import com.yapp.project.review.dto.response.TextReviewSimpleResponse;
import com.yapp.project.review.service.TextReviewHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "작성하는 리뷰")
public class TextReviewHistoryController {
    private final TextReviewHistoryService textReviewHistoryService;
    private final JwtService jwtService;

    @ApiOperation("특정 사용자 프로필에 '작성하는 리뷰' 등록")
    @PostMapping(value = "/members/{memberId}/text-reviews")
    public ResponseEntity<ApiResult> insert(
            @RequestHeader("accessToken") String accessToken,
            @RequestBody TextReviewCreateRequest request
    ) {

        Long reviewerId = jwtService.getMemberId(accessToken);

        textReviewHistoryService.create(reviewerId, request);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS)
        );
    }

    @ApiOperation("특정 사용자의 텍스트리뷰 모두 조회")
    @GetMapping(value = "/members/{memberId}/text-reviews")
    public ResponseEntity<ApiResult> getAll(@PathVariable Long memberId, Pageable pageable) {
        Page<TextReviewSimpleResponse> response = textReviewHistoryService.findAllByPages(memberId, pageable);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }
}
