package com.yapp.project.member.controller;

import com.yapp.project.member.service.MemberService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/members")
@Api(tags = "Member")
public class MemberController {
    private final MemberService memberService;

}
