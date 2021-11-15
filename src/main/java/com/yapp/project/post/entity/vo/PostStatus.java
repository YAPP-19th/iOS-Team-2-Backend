package com.yapp.project.post.entity.vo;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PostStatus {
    RECRUITING(0, "모집중"),
    RECRUITMENT_COMPLETED(1, "모집완료"),
    PROJECT_IN_PROGRESS(2, "프로젝트_진행중"),
    PROJECT_COMPLETED(3, "프로젝트_완료");

    private final int postStatusCode;
    private final String postStatusName;

    PostStatus(int postStatusCode, String postStatusName) {
        this.postStatusCode = postStatusCode;
        this.postStatusName = postStatusName;
    }

    public static PostStatus of(String postStatusName) {
        return Arrays.stream(PostStatus.values())
                .filter(v -> v.postStatusName.equals(postStatusName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_STATUS));
    }

    public static PostStatus of(int postStatusCode) {
        return Arrays.stream(PostStatus.values())
                .filter(v -> v.postStatusCode == postStatusCode)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_STATUS));
    }
}
