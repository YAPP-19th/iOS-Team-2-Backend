package com.yapp.project.member.service;

import com.yapp.project.member.dto.CheckNameResponse;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {
    public CheckNameResponse toCheckNameResponse(
            boolean available
    ) {
        return CheckNameResponse.builder()
                .available(!available)
                .build();
    }
}
