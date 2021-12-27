package com.yapp.project.review.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.member.service.JwtService;
import com.yapp.project.review.dto.request.TextReviewCreateRequest;
import com.yapp.project.review.dto.response.TextReviewSimpleResponse;
import com.yapp.project.review.service.TextReviewHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    @PostMapping(value = "/text-reviews")
    public ResponseEntity<ApiResult> insert(
            @RequestHeader("accessToken") String accessToken,
            @RequestParam(required = true) long memberId,
            @RequestParam(required = true) long postId,
            @RequestBody TextReviewCreateRequest request
    ) {

        jwtService.validateTokenForm(accessToken);

        long reviewerId = jwtService.getMemberId(accessToken);

        textReviewHistoryService.create(reviewerId, memberId, postId, request);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS)
        );
    }

    @ApiOperation("특정 사용자의 텍스트리뷰 모두 조회")
    @GetMapping(value = "/text-reviews")
    public ResponseEntity<ApiResult> getAll(
            @RequestParam(required = true) @Parameter(description = "100 입력하세요") long memberId,
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC, size = 20) Pageable pageable
    ) {

        Page<TextReviewSimpleResponse> response = textReviewHistoryService.findAllByPages(memberId, pageable);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }
}
