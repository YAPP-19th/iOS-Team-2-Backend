package com.yapp.project.member.controller;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.member.dto.CheckNameResponse;
import com.yapp.project.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/members", produces = MediaTypes.HAL_JSON_VALUE)
@Api(tags = "Member")
public class MemberController {
    private final MemberService memberService;

    @ApiOperation("닉네임 중복 확인")
    @GetMapping(value = "/checkDuplicateName")
    public ResponseEntity<ApiResult> checkDuplicateName(@RequestParam("name") String name) {
        CheckNameResponse response = memberService.checkDuplicateName(name);
        EntityModel<CheckNameResponse> entityModel = EntityModel.of(
                response
        );

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.CHECK_AVAILABLE_NICKNAME, entityModel)
        );
    }
}
