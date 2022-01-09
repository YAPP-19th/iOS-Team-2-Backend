package com.yapp.project.review.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.member.service.JwtService;
import com.yapp.project.review.dto.response.TeamResponse;
import com.yapp.project.review.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
@Api(tags = "버디 평가")
@Validated
public class ReviewController {
    private final JwtService jwtService;
    private final ReviewService reviewService;

    @ApiOperation(value = "프로젝트에 참여한 팀원 전체 조회", notes = "현재 유저의 리뷰 작성 가능 여부를 포함한 팀원의 정보를 응답합니다 \n cf. 나의 버디 -> 참여 프로젝트 -> 완료 -> 팀원 평가하기")
    @GetMapping(value = "/posts/{postId}/members/reviews")
    public ResponseEntity<ApiResult> getAllTeamMembers(
            @PathVariable(required = true) @Positive long postId,
            @RequestHeader(value = "accessToken", required = false) @NotBlank String accessToken
    ) {

        jwtService.validateTokenForm(accessToken);

        long memberId = jwtService.getMemberId(accessToken);

        List<TeamResponse> response = reviewService.getAllTeamMembers(postId, memberId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }
}
