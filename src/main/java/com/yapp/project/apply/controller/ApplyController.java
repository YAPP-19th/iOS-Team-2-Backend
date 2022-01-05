package com.yapp.project.apply.controller;

import com.yapp.project.apply.dto.request.ApplyRequest;
import com.yapp.project.apply.dto.response.ApplicantResponse;
import com.yapp.project.apply.dto.response.ApplyResponse;
import com.yapp.project.apply.service.ApplyService;
import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.member.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/applies", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "지원하기")
@Validated
public class ApplyController {
    private final ApplyService applyService;
    private final JwtService jwtService;

    @ApiOperation("지원하기")
    @PostMapping()
    public ResponseEntity<ApiResult> insert(@Valid @RequestBody ApplyRequest request, @RequestHeader("accessToken") @NotBlank String accessToken) throws IOException {
        jwtService.validateTokenForm(accessToken);

        long memberId = jwtService.getMemberId(accessToken);

        ApplyResponse response = applyService.apply(memberId, request);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("지원 승인하기")
    @PatchMapping(value = "/{applyId}")
    public ResponseEntity<ApiResult> approveApplication(@PathVariable @Positive long applyId, @RequestHeader("accessToken") @NotBlank String accessToken) {
        jwtService.validateTokenForm(accessToken);

        long memberId = jwtService.getMemberId(accessToken); // TODO: 예외처리

        applyService.approveApplication(applyId, memberId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS)
        );
    }

    @ApiOperation("지원 취소하기")
    @DeleteMapping(value = "/{applyId}")
    public ResponseEntity<ApiResult> cancelApplication(@PathVariable @Positive long applyId, @RequestHeader("accessToken") @NotBlank String accessToken) {
        jwtService.validateTokenForm(accessToken);

        long memberId = jwtService.getMemberId(accessToken);

        applyService.cancelApplication(applyId, memberId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS)
        );
    }

    @ApiOperation(value = "지원자 조회", notes = "developer / designer / planner 는 선택사항입니다.")
    @GetMapping()
    public ResponseEntity<ApiResult> getApplyList(
            @RequestParam @Positive long postId,
            @RequestParam @Nullable String position,
            @RequestHeader("accessToken") @NotBlank String accessToken
    ) {

        long leaderId = jwtService.getMemberId(accessToken);

        List<ApplicantResponse> response = applyService.getApplyList(postId, Optional.ofNullable(position), leaderId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }
}
