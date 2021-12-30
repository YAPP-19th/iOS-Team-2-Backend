package com.yapp.project.post.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.member.service.JwtService;
import com.yapp.project.post.dto.request.PostCreateRequest;
import com.yapp.project.post.dto.request.PostUpdateRequest;
import com.yapp.project.post.dto.response.*;
import com.yapp.project.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/posts", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "프로젝트 관련")
@Validated
public class PostController {
    private final PostService postService;

    private final JwtService jwtService;

    @ApiOperation("게시글 생성")
    @PostMapping()
    public ResponseEntity<ApiResult> insert(
            @Valid @RequestBody PostCreateRequest request,
            @RequestHeader("accessToken") @NotBlank String accessToken
    ) {

        jwtService.validateTokenForm(accessToken);

        var response = postService.create(request, accessToken);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("게시글 수정 (모집정보 수정 불가)")
    @PutMapping(value = "/{postId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult> update(
            @PathVariable @Positive long postId,
            @Valid @RequestBody PostUpdateRequest request,
            @RequestHeader("accessToken") @NotBlank String accessToken
    ) {

        jwtService.validateTokenForm(accessToken);

        postService.update(postId, request, accessToken);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS)
        );
    }

    @ApiOperation("게시글 단건 조회 (전체 틀)")
    @GetMapping(value = "/{postId}")
    public ResponseEntity<ApiResult> getOne(
            @PathVariable @Positive long postId,
            @Nullable @RequestHeader("accessToken") String accessToken
    ) {

        PostDetailResponse response = postService.findById(postId, Optional.ofNullable(accessToken));

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("게시글 단건 조회 (참여승인된 멤버만 가져옴)")
    @GetMapping(value = "/{postId}/members")
    public ResponseEntity<ApiResult> getTeamMembers(@PathVariable @Positive Long postId) {
        TeamMemberResponse response = postService.findTeamMembersById(postId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("게시글 단건 조회 (지원 현황)")
    @GetMapping(value = "/{postId}/recruitingStatus")
    public ResponseEntity<ApiResult> getRecruitingStatus(@PathVariable @Positive long postId) {
        var response = postService.findRecruitingStatusById(postId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("게시글 전체 조회")
    @GetMapping()
    public ResponseEntity<ApiResult> getAll(
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC, size = 20) Pageable pageable
    ) {

        Page<PostSimpleResponse> response = postService.findAllByPages(pageable);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("position으로 조회")
    @GetMapping(value = "/positions/{basePositionName}")
    public ResponseEntity<ApiResult> getAllByPosition(
            @PathVariable String basePositionName,
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC, size = 20) Pageable pageable
    ) {

        Page<PostSimpleResponse> response = postService.findAllByPosition(basePositionName, pageable);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("게시글 단건 삭제")
    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<ApiResult> deleteOne(
            @RequestHeader("accessToken") @NotBlank String accessToken,
            @PathVariable @Positive long postId
    ) {

        jwtService.validateTokenForm(accessToken);

        PostDeleteResponse response = postService.deleteById(accessToken, postId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    //TODO: 내가 모집중인 전체 프로젝트
}
