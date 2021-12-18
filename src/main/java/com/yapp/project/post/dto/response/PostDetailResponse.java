package com.yapp.project.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class PostDetailResponse {
    private final Long postId;

    private final String imageUrl;

    private final String title;

    private final String category;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime endDate;

    private final String region;

    private final String description;

    private final Long viewCount;

    private final String status;

    private final String onlineInfo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime modifiedAt;

    private final MemberDto leader;

    @Getter
    @RequiredArgsConstructor
    public static class MemberDto{
        private final Long leaderId;

        private final String nickName;

        private final String profileImageUrl;

        private final String address;

        private final String position;
    }
}
