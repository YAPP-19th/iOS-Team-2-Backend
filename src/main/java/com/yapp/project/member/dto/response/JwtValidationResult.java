package com.yapp.project.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class JwtValidationResult {
    @Nullable
    private String userId;
    @NotNull
    private boolean validation;
}
