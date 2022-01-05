package com.yapp.project.info.controller;

import com.yapp.project.common.value.PostDefaultImage;
import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.info.service.DefaultInfoService;
import com.yapp.project.review.service.CodeReviewHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/infos")
@Api(tags = "앱 운영 정보")
@Validated
public class InfoController {
    private final DefaultInfoService defaultInfoService;

    @ApiOperation(value = "직무 리스트", notes = "developer / planner / designer")
    @GetMapping(value = "/positions")
    public ResponseEntity<ApiResult> getPositionList(@RequestParam("position") @NotBlank String position) {
        List list = defaultInfoService.getPostionInfo(position);
        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, list)
        );
    }

    @ApiOperation(value = "게시글 카테고리 리스트")
    @GetMapping(value = "/postCategory")
    public ResponseEntity<ApiResult> getPostCategoryList() {
        List<String> response = defaultInfoService.getPostCategoryList();
        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation(value = "기본 게시글 이미지 URL", notes = "모든 게시글 기본 이미지 URL")
    @GetMapping(value = "/postDefaultImageUrls")
    public ResponseEntity<ApiResult> getAllPostDefaultImageUrls() {
        var response = PostDefaultImage.getAllUrls();

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation(value = "리뷰 '목록' 전체 조회", notes = "선택하는 리뷰 목록 전체 조회")
    @GetMapping(value = "/select-reviews-list")
    public ResponseEntity<ApiResult> getAllSelectReviewList() {
        var response = defaultInfoService.findAllReviews();

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation(value = "게시글 상태정보 전체 조회", notes = "게시글 상태정보 수정시 이용바랍니다")
    @GetMapping(value = "/postStatus")
    public ResponseEntity<ApiResult> getAllPostStatus() {
        var response = defaultInfoService.findAllPostStatus();

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    //TODO: 위치 정보 제공 API 추가
}
