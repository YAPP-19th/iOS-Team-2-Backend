package com.yapp.project.likepost.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.likepost.dto.LikePostResponse;
import com.yapp.project.likepost.dto.LikeStatusResponse;
import com.yapp.project.likepost.service.LikePostService;
import com.yapp.project.member.service.JwtService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/post")
@Api(tags = "관심 프로젝트 찜하기")
@Validated
public class LikePostController {
    private final LikePostService likePostService;
    private final JwtService jwtService;

    @ApiOperation("게시글 좋아요 상태 변경")
    @PutMapping(value = "/{postId}/like-posts")
    public ResponseEntity<ApiResult> switchLikeStatus(@PathVariable @Positive long postId, @RequestHeader("accessToken") @NotBlank String accessToken) {
        jwtService.validateTokenForm(accessToken);

        Long memberId = jwtService.getMemberId(accessToken);
        LikeStatusResponse response = likePostService.switchLikeStatus(memberId, postId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("내가 좋아한 모든 게시글(프로젝트)")
    @GetMapping(value = "/like-posts")
    public ResponseEntity<ApiResult> getAll(
            @RequestHeader("accessToken") @NotBlank String accessToken,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 20) Pageable pageable
    ) {
        jwtService.validateTokenForm(accessToken);

        long memberId = jwtService.getMemberId(accessToken);
        Page<LikePostResponse> response = likePostService.findAll(pageable, memberId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }
}
