package com.yapp.project.likepost.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikeStatusResponse {
    private final Boolean isLiked;

    private final long likeCount;
}
