package com.yapp.project.likepost.service;

import com.yapp.project.common.value.PostCategory;
import com.yapp.project.likepost.dto.LikePostResponse;
import com.yapp.project.likepost.entity.LikePost;
import com.yapp.project.post.entity.value.OnlineStatus;
import com.yapp.project.post.entity.value.PostStatus;
import org.springframework.stereotype.Component;

@Component
public class LikePostConverter {
    public LikePostResponse toLikePostResponse(LikePost likePost) {
        var post = likePost.getPost();

        return LikePostResponse.builder()
                .postId(post.getId())
                .imageUrl(post.getImageUrl())
                .title(post.getTitle())
                .startDate(post.getStartDate())
                .endDate(post.getEndDate())
                .onlineInfo(OnlineStatus.of(post.getOnlineCode()).getName())
                .category(PostCategory.of(post.getCategoryCode()).getName())
                .likeCount(post.getLikeCount())
                .build();
    }
}
