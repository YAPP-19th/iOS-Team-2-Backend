package com.yapp.project.review.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.member.service.JwtService;
import com.yapp.project.review.dto.request.TextReviewCreateRequest;
import com.yapp.project.review.dto.response.CodeReviewCountResponse;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "작성하는 리뷰")
@Validated
public class TextReviewHistoryController {
    private final TextReviewHistoryService textReviewHistoryService;
    private final JwtService jwtService;

    @ApiOperation("특정 사용자 프로필에 '작성하는 리뷰' 등록")
    @PostMapping(value = "/text-reviews")
    public ResponseEntity<ApiResult> insert(
            @RequestHeader("accessToken") @NotBlank String accessToken,
            @RequestParam(required = true) @Positive long memberId,
            @RequestParam(required = true) @Positive long postId,
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
            @RequestParam(required = true) @Parameter(description = "100 입력하세요") @Positive long memberId,
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC, size = 20) Pageable pageable
    ) {

        Page<TextReviewSimpleResponse> response = textReviewHistoryService.findAllByPages(memberId, pageable);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation(value = "'특정 프로젝트에서' 내가 받은 '텍스트 리뷰' 개수", notes = "'나의버디 -> 참여프로젝트 -> 완료-> 내 평가보기' 결과입니다")
    @GetMapping(value = "/text-reviews/posts/{postId}")
    public ResponseEntity<ApiResult> getAllByPost(
            @PathVariable(required = true) @Positive long postId,
            @RequestHeader("accessToken") @NotBlank String accessToken
    ) {

        jwtService.validateTokenForm(accessToken);

        long currentMemberId = jwtService.getMemberId(accessToken);

        List<TextReviewSimpleResponse> response = textReviewHistoryService.findAllByMemberAndPost(currentMemberId, postId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }
}
