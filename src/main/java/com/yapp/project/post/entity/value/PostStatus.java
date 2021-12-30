package com.yapp.project.post.entity.value;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PostStatus {
    RECRUITING(0, "모집 진행중"),
    RECRUITMENT_COMPLETED(1, "모집 완료"),
    PROJECT_IN_PROGRESS(2, "프로젝트 진행중"),
    PROJECT_COMPLETED(3, "프로젝트 완료");

    private final int code;
    private final String name;

    PostStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PostStatus of(String postStatusName) {
        return Arrays.stream(PostStatus.values())
                .filter(v -> v.name.equals(postStatusName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_STATUS));
    }

    public static PostStatus of(int postStatusCode) {
        return Arrays.stream(PostStatus.values())
                .filter(v -> v.code == postStatusCode)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_STATUS));
    }
}
