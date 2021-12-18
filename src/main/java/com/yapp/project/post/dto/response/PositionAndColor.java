package com.yapp.project.post.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PositionAndColor {
    private final String position;

    private final int colorCode;
}
