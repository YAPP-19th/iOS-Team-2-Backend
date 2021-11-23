package com.yapp.project.post.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.post.controller.bundle.PostBundleConverter;
import com.yapp.project.post.dto.request.PostCreateRequest;
import com.yapp.project.post.dto.response.PostDeleteResponse;
import com.yapp.project.post.dto.response.PostInfoResponse;
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
    private final PostBundleConverter postBundleConverter;

    @ApiOperation("게시글 생성")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResult> insert(@Valid @ModelAttribute PostCreateRequest request) throws IOException {
        var response = postService.create(
                request.getTitle(),
                request.getCategoryName(),
                request.getStartDate(),
                request.getEndDate(),
                request.getRegion(),
                request.getDescription(),
                request.getOwnerId(),
                request.getOnlineInfo(),
                request.getPostImages(),
                postBundleConverter.toTeamMemberRequestBundle(request.getRecruitingPositionRequests())
        );

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.POST_INSERT_SUCCESS, response)
        );
    }

    @ApiOperation("게시글 단건 조회")
    @GetMapping(value = "/{postId}")
    public ResponseEntity<ApiResult> getOne(@PathVariable Long postId) {
        PostInfoResponse response = postService.findById(postId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.POST_SEARCH_SUCCESS, response)
        );
    }

    @ApiOperation("게시글 전체 조회")
    @GetMapping()
    public ResponseEntity<ApiResult> getAll(Pageable pageable) {
        Page<PostInfoResponse> response = postService.findAllByPages(pageable);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.POST_SEARCH_SUCCESS, response)
        );
    }

    @ApiOperation("position으로 조회")
    @GetMapping(value = "/positions/{rootPositionName}")
    public ResponseEntity<ApiResult> getAllByPosition(@PathVariable String rootPositionName, Pageable pageable) {
        Page<PostInfoResponse> response = postService.findAllByPosition(rootPositionName, pageable);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.POST_SEARCH_SUCCESS, response)
        );
    }

    @ApiOperation("게시글 단건 삭제")
    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<ApiResult> deleteOne(@PathVariable Long postId) {
        PostDeleteResponse response = postService.deleteById(postId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.POST_DELETE_SUCCESS, response)
        );
    }
}
