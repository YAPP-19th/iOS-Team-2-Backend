package com.yapp.project.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class MyBudiProjectResponse {
    private List<ProjectSimple> participatedPosts;

    private List<ProjectSimple> recruitedPosts;

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ProjectSimple{
        private final long id;

        private final String imageUrl;

        private final String title;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime startDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime endDate;

        private final String onlineInfo;

        private final String category;

        private final Long likeCount;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final String appliedStatus;
    }
}
