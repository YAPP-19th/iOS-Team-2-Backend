package com.yapp.project.notification.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.member.service.JwtService;
import com.yapp.project.notification.dto.NotificationResponse;
import com.yapp.project.notification.service.NotificationService;
import com.yapp.project.post.dto.request.PostCreateRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "푸시알림")
@Validated
public class NotificationController {
    private final NotificationService notificationService;
    private final JwtService jwtService;


    @ApiOperation("받은 알림 전체 조회")
    @GetMapping()
    public ResponseEntity<ApiResult> getAll(
            @RequestHeader("accessToken") @NotBlank String accessToken,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 20) Pageable pageable
    ) {

        jwtService.validateTokenForm(accessToken);

        long receiverId = jwtService.getMemberId(accessToken);

        Page<NotificationResponse> response = notificationService.findAllByMember(pageable, receiverId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("읽음으로 변경")
    @PatchMapping(value = "/{notificationId}")
    public ResponseEntity<ApiResult> toggleReadStatus(
            @PathVariable @Positive long notificationId,
            @RequestHeader("accessToken") @NotBlank String accessToken
    ) {

        jwtService.validateTokenForm(accessToken);

        long receiverId = jwtService.getMemberId(accessToken);

        notificationService.toggleReadStatus(notificationId, receiverId);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS)
        );
    }

    @ApiOperation("안읽은 알림 개수 조회")
    @GetMapping(value = "/unreadCount")
    public ResponseEntity<ApiResult> getUnreadCount(@RequestHeader("accessToken") @NotBlank String accessToken) {

        jwtService.validateTokenForm(accessToken);

        long currentMemberId = jwtService.getMemberId(accessToken);

        int count = notificationService.getUnreadCount(currentMemberId);

        var response = new HashMap<>();
        response.put("unreadCount", count);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }
}
