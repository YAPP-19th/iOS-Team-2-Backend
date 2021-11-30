package com.yapp.project.post.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.post.dto.request.PostCreateRequest;
import com.yapp.project.post.dto.response.*;
import com.yapp.project.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/posts", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Post (Project)")
public class PostController {
    private final PostService postService;

    @ApiOperation("게시글 생성")
    @PostMapping(consumes = {"multipart/form-data"})// 변경 전 multipart/form-data
    public ResponseEntity<ApiResult> insert(@Valid @ModelAttribute PostCreateRequest request, @RequestHeader("accessToken") String accessToken) throws IOException {
        var response = postService.create(request, accessToken);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("게시글 단건 조회 (전체 틀)")
    @GetMapping(value = "/{postId}")
    public ResponseEntity<ApiResult> getOne(@PathVariable Long postId) {
        PostDetailResponse response = postService.findById(postId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("게시글 단건 조회 (확정 멤버)")
    @GetMapping(value = "/{postId}/members")
    public ResponseEntity<ApiResult> getTeamMembers(@PathVariable Long postId) {
        TeamMemberResponse response = postService.findTeamMembersById(postId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("게시글 단건 조회 (지원 현황)")
    @GetMapping(value = "/{postId}/recruitingStatus")
    public ResponseEntity<ApiResult> getRecruitingStatus(@PathVariable Long postId) {
        var response = postService.findRecruitingStatusById(postId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("게시글 전체 조회")
    @GetMapping()
    public ResponseEntity<ApiResult> getAll(Pageable pageable) {
        Page<PostSimpleResponse> response = postService.findAllByPages(pageable);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("position으로 조회")
    @GetMapping(value = "/positions/{rootPositionName}")
    public ResponseEntity<ApiResult> getAllByPosition(@PathVariable String rootPositionName, Pageable pageable) {
        Page<PostSimpleResponse> response = postService.findAllByPosition(rootPositionName, pageable);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("게시글 단건 삭제")
    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<ApiResult> deleteOne(@RequestHeader("accessToken") String accessToken, @PathVariable Long postId) {
        PostDeleteResponse response = postService.deleteById(accessToken, postId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }
}
