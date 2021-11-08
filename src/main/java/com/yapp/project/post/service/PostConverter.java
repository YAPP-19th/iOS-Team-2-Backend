package com.yapp.project.post.service;

import com.yapp.project.member.entity.Member;
import com.yapp.project.post.dto.response.PostCreateResponse;
import com.yapp.project.post.dto.response.PostInfoResponse;
import com.yapp.project.post.dto.response.RecruitingStatusResponse;
import com.yapp.project.post.entity.Post;
import com.yapp.project.post.entity.vo.PostCategory;
import com.yapp.project.post.entity.vo.PostStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class PostConverter {
    public Post toPostEntity(
            String title,
            int categoryCode,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String region,
            String description,
            String imageUrls,
            Member owner
    ) {

        return Post.builder()
                .title(title)
                .postCategory(PostCategory.of(categoryCode).getCategoryCode())
                .startDate(startDate)
                .endDate(endDate)
                .region(region)
                .description(description)
                .owner(owner)
                .postStatus(PostStatus.RECRUITING)
                .viewCount(0L)
                .imageUrls(imageUrls)
                .build();
    }

    public PostCreateResponse toPostCreateResponse(
            Long id,
            List<String> imageUrls,
            int categoryCode,
            LocalDateTime createdAt) {

        return new PostCreateResponse(id, imageUrls, PostCategory.of(categoryCode).name(), createdAt);
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
                .postStatus(postEntity.getPostStatus().name())
                .postCategory(PostCategory.of(postEntity.getPostCategory()).name())
                .ownerId(postEntity.getOwner().getId())
                .createdAt(postEntity.getCreatedDate())
                .modifiedAt(postEntity.getLastModifiedDate())
                .recruitingStatusResponses(recruitingStatusResponses)
                .build();
    }
}
