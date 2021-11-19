package com.yapp.project.post.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.LinkType;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.post.controller.bundle.PostBundleConverter;
import com.yapp.project.post.dto.request.PostCreateRequest;
import com.yapp.project.post.dto.response.PostCreateResponse;
import com.yapp.project.post.dto.response.PostInfoResponse;
import com.yapp.project.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/posts", produces = MediaTypes.HAL_JSON_VALUE)
@Api(tags = "Post (Project)")
public class PostController {
    private final PostService postService;
    private final PostBundleConverter postBundleConverter;

    private String linkWithPostId = "/{postId}";

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(PostController.class);
    }

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

        EntityModel<PostCreateResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getPostId()).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getPostId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getPostId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.POST_INSERT_SUCCESS, entityModel)
        );
    }

    @ApiOperation("게시글 단건 조회")
    @GetMapping(value = "/{postId}")
    public ResponseEntity<ApiResult> getOne(@Valid @PathVariable Long postId) {
        PostInfoResponse response = postService.findById(postId);

        EntityModel<PostInfoResponse> entityModel = EntityModel.of(
                response,
                getLinkToAddress().withRel(LinkType.CREATE_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getPostId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(LinkType.READ_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getPostId()).withRel(LinkType.UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getPostId()).withRel(LinkType.DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.POST_SEARCH_SUCCESS, entityModel)
        );
    }

    @ApiOperation("게시글 전체 조회")
    @GetMapping()
    public ResponseEntity<ApiResult> getAll(Pageable pageable) {
        Page<PostInfoResponse> response = postService.findAllByPages(pageable);

        EntityModel<Page<PostInfoResponse>> entityModel = EntityModel.of(
                response,
                getLinkToAddress().slash(linkWithPostId).withRel(LinkType.READ_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name())
        );

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.POST_SEARCH_SUCCESS, entityModel)
        );
    }
}
