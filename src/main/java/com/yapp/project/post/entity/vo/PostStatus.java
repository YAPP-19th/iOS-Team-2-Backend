package com.yapp.project.post.entity.vo;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PostStatus {
    RECRUITING("모집중"),
    RECRUITMENT_COMPLETED("모집완료"),
    PROJECT_IN_PROGRESS("프로젝트_진행중"),
    PROJECT_COMPLETED("프로젝트_완료");

    private final String postStatus;

    PostStatus(String postStatus) {
        this.postStatus = postStatus;
    }

    public static PostStatus of(String postStatus) {
        return Arrays.stream(PostStatus.values())
                .filter(v -> v.postStatus.equals(postStatus))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_STATUS));
    }
}
