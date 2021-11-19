package com.yapp.project.post.service;

import com.yapp.project.common.value.Position;
import com.yapp.project.common.value.Skill;
import com.yapp.project.member.entity.Member;
import com.yapp.project.post.dto.response.PostCreateResponse;
import com.yapp.project.post.dto.response.PostDeleteResponse;
import com.yapp.project.post.dto.response.PostInfoResponse;
import com.yapp.project.post.dto.response.RecruitingStatusResponse;
import com.yapp.project.post.entity.Post;
import com.yapp.project.post.entity.RecruitingPosition;
import com.yapp.project.post.entity.value.OnlineStatus;
import com.yapp.project.post.entity.value.PostCategory;
import com.yapp.project.post.entity.value.PostStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PostConverter {
    public Post toPostEntity(
            String title,
            String categoryName,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String region,
            String description,
            String onlineInfo,
            String imageUrls,
            Member owner
    ) {

        return Post.builder()
                .title(title)
                .categoryCode(PostCategory.of(categoryName).getCategoryCode())
                .startDate(startDate)
                .endDate(endDate)
                .region(region)
                .description(description)
                .owner(owner)
                .statusCode(PostStatus.RECRUITING.getPostStatusCode())
                .viewCount(0L)
                .onlineCode(OnlineStatus.of(onlineInfo).getOnlineStatusCode())
                .imageUrls(imageUrls)
                .build();
    }

    public PostCreateResponse toPostCreateResponse(
            Long id,
            List<String> imageUrls,
            int categoryCode,
            LocalDateTime createdAt) {

        return new PostCreateResponse(id, imageUrls, PostCategory.of(categoryCode).getCategoryName(), createdAt);
    }

    public PostInfoResponse toPostInfoResponse(Post postEntity, List<RecruitingStatusResponse> recruitingStatusResponses) {

        return PostInfoResponse.builder()
                .postId(postEntity.getId())
                .imageUrls(Arrays.asList(postEntity.getImageUrls().split(" ")))
                .title(postEntity.getTitle())
                .description(postEntity.getDescription())
                .startDate(postEntity.getStartDate())
                .endDate(postEntity.getEndDate())
                .region(postEntity.getRegion())
                .viewCount(postEntity.getViewCount())
                .status(PostStatus.of(postEntity.getStatusCode()).getPostStatusName())
                .category(PostCategory.of(postEntity.getCategoryCode()).getCategoryName())
                .ownerId(postEntity.getOwner().getId())
                .onlineInfo(OnlineStatus.of(postEntity.getOnlineCode()).getOnlineStatusName())
                .createdAt(postEntity.getCreatedDate())
                .modifiedAt(postEntity.getLastModifiedDate())
                .recruitingStatusResponses(recruitingStatusResponses)
                .build();
    }

    public PostInfoResponse toPostInfoResponse(RecruitingPosition recruitingPosition, String recruitiongStatus) {
        Post postEntity = recruitingPosition.getPost();

        var recruitingStatusResponses = new ArrayList<RecruitingStatusResponse>();
        recruitingStatusResponses.add(
                new RecruitingStatusResponse(
                        recruitingPosition.getId(),
                        Position.of(recruitingPosition.getPositionCode()).getPositionName(),
                        Position.of(recruitingPosition.getPositionCode()).getPositionCode(),
                        Skill.of(recruitingPosition.getSkillCode()).getSkillName(),
                        Skill.of(recruitingPosition.getSkillCode()).getSkillCode(),
                        recruitiongStatus
                )
        );

        return PostInfoResponse.builder()
                .postId(postEntity.getId())
                .imageUrls(Arrays.asList(postEntity.getImageUrls().split(" ")))
                .title(postEntity.getTitle())
                .description(postEntity.getDescription())
                .startDate(postEntity.getStartDate())
                .endDate(postEntity.getEndDate())
                .region(postEntity.getRegion())
                .viewCount(postEntity.getViewCount())
                .status(PostStatus.of(postEntity.getStatusCode()).getPostStatusName())
                .category(PostCategory.of(postEntity.getCategoryCode()).getCategoryName())
                .ownerId(postEntity.getOwner().getId())
                .onlineInfo(OnlineStatus.of(postEntity.getOnlineCode()).getOnlineStatusName())
                .createdAt(postEntity.getCreatedDate())
                .modifiedAt(postEntity.getLastModifiedDate())
                .recruitingStatusResponses(recruitingStatusResponses)
                .build();
    }

    public PostDeleteResponse toPostDeleteResponse(Long postId) {
        return new PostDeleteResponse(postId);
    }
}
