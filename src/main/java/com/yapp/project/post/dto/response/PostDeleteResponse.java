package com.yapp.project.post.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostDeleteResponse {
    private final Long postId;
}
