package com.yapp.project.post.service;

import com.yapp.project.member.entity.Member;
import com.yapp.project.post.dto.response.*;
import com.yapp.project.post.entity.Post;
import com.yapp.project.post.entity.value.OnlineStatus;
import com.yapp.project.post.entity.value.PostCategory;
import com.yapp.project.post.entity.value.PostStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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

    // used
    public PostDetailResponse toPostDetailResponse(Post post, Member leader){
        return PostDetailResponse.builder()
                .postId(post.getId())
                .imageUrls(Arrays.asList(post.getImageUrls().split(" ")))
                .title(post.getTitle())
                .description(post.getDescription())
                .startDate(post.getStartDate())
                .endDate(post.getEndDate())
                .region(post.getRegion())
                .viewCount(post.getViewCount())
                .status(PostStatus.of(post.getStatusCode()).getPostStatusName())
                .category(PostCategory.of(post.getCategoryCode()).getCategoryName())
                .leader(
                        new PostDetailResponse.MemberDto(
                                leader.getId(),
                                leader.getNickName(),
                                leader.getProfileImageUrl(),
                                leader.getAddress()
                        )
                )
                .onlineInfo(OnlineStatus.of(post.getOnlineCode()).getOnlineStatusName())
                .createdAt(post.getCreatedDate())
                .modifiedAt(post.getLastModifiedDate())
                .build();
    }

    public PostSimpleResponse toPostSimpleResponse(Post post, List<String> positions) {
        return PostSimpleResponse.builder()
                .postId(post.getId())
                .imageUrls(Arrays.asList(post.getImageUrls().split(" ")))
                .title(post.getTitle())
                .startDate(post.getStartDate())
                .endDate(post.getEndDate())
                .region(post.getRegion())
                .createdAt(post.getCreatedDate())
                .modifiedAt(post.getLastModifiedDate())
                .positions(positions)
                .build();
    }

    public PostDeleteResponse toPostDeleteResponse(Long postId) {
        return new PostDeleteResponse(postId);
    }
}
