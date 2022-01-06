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

    @ApiOperation("게시글 단건 조회 (참여승인된 멤버 조회)")
    @GetMapping(value = "/{postId}/members")
    public ResponseEntity<ApiResult> getTeamMembers(@PathVariable @Positive Long postId) {
        TeamMemberResponse response = postService.findTeamMembersById(postId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("게시글 단건 조회 (모집 정보 조회)")
    @GetMapping(value = "/{postId}/recruitingStatus")
    public ResponseEntity<ApiResult> getRecruitingStatus(@PathVariable @Positive long postId) {
        var response = postService.findRecruitingStatusByPostId(postId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation(value = "게시글 전체 조회 / 프로젝트 title로 검색", notes = "parameter title을 지정하지 않을 시 전체게시글을 조회하며 title을 지정하면 title로 검색합니다")
    @GetMapping()
    public ResponseEntity<ApiResult> getAll(
            @RequestParam(value = "title", required = false) @Nullable String title,
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC, size = 20) Pageable pageable
    ) {

        Page<PostSimpleResponse> response = postService.findAllByPages(pageable, Optional.ofNullable(title));

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation(value = "position으로 조회", notes = "개발 / 기획 / 디자인")
    @GetMapping(value = "/positions/{basePositionName}")
    public ResponseEntity<ApiResult> getAllByPosition(
            @PathVariable String basePositionName,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 20) Pageable pageable
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

    @ApiOperation("게시글 상태정보 수정")
    @PatchMapping(value = "/{postId}/{postStatusCode}")
    public ResponseEntity<ApiResult> switchPostStatus(
            @RequestHeader("accessToken") @NotBlank String accessToken,
            @PathVariable @Positive long postId,
            @PathVariable @Positive int postStatusCode
    ) {

        jwtService.validateTokenForm(accessToken);

        postService.switchPostStatus(accessToken, postStatusCode, postId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS)
        );
    }

    //TODO: 내가 모집중인 전체 프로젝트 + 내가 참여한 프로젝트
}
