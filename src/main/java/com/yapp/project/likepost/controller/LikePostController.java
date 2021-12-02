package com.yapp.project.likepost.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.likepost.dto.LikePostResponse;
import com.yapp.project.likepost.service.LikePostService;
import com.yapp.project.member.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/post")
@Api(tags = "관심 프로젝트 찜하기")
public class LikePostController {
    private final LikePostService likePostService;
    private final JwtService jwtService;

    @ApiOperation("프로젝트(게시글) 좋아요 누르기")
    @PostMapping(value = "/{postId}/like-posts")
    public ResponseEntity<ApiResult> insert(@PathVariable Long postId, @RequestHeader("accessToken") String accessToken) {
        Long memberId = jwtService.getMemberId(accessToken);
        likePostService.like(memberId, postId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS)
        );
    }

    @ApiOperation("게시글 좋아요 취소")
    @DeleteMapping(value = "/{postId}/like-posts")
    public ResponseEntity<ApiResult> delete(@PathVariable Long postId, @RequestHeader("accessToken") String accessToken) {
        Long memberId = jwtService.getMemberId(accessToken);
        likePostService.cancelLike(memberId, postId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS)
        );
    }

    @ApiOperation("내가 좋아한 모든 게시글(프로젝트)")
    @GetMapping(value = "/like-posts")
    public ResponseEntity<ApiResult> getAll(@RequestHeader("accessToken") String accessToken) {
        Long memberId = jwtService.getMemberId(accessToken);
        LikePostResponse response = likePostService.findAll(memberId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }
}
