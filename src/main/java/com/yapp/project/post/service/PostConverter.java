package com.yapp.project.post.service;

import com.yapp.project.apply.entity.Apply;
import com.yapp.project.common.dto.PositionAndColor;
import com.yapp.project.common.util.PositionParser;
import com.yapp.project.common.value.Position;
import com.yapp.project.member.entity.Member;
import com.yapp.project.post.dto.request.PostCreateRequest;
import com.yapp.project.post.dto.response.*;
import com.yapp.project.post.dto.value.AppliedProjectStatus;
import com.yapp.project.post.entity.Post;
import com.yapp.project.post.entity.value.OnlineStatus;
import com.yapp.project.common.value.PostCategory;
import com.yapp.project.post.entity.value.PostStatus;
import com.yapp.project.review.dto.response.TeamResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostConverter {
    public Post toPostEntity(PostCreateRequest request, Member leader) {

        return Post.builder()
                .title(request.getTitle())
                .categoryCode(PostCategory.of(request.getCategoryName()).getCode())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .region(request.getRegion())
                .description(request.getDescription())
                .owner(leader)
                .statusCode(PostStatus.RECRUITING.getCode())
                .viewCount(0L)
                .onlineCode(OnlineStatus.of(request.getOnlineInfo()).getCode())
                .imageUrl(request.getImageUrl())
                .likeCount(0L)
                .build();
    }

    public PostCreateResponse toPostCreateResponse(
            Long id,
            String imageUrl,
            int categoryCode,
            LocalDateTime createdAt) {

        return new PostCreateResponse(id, imageUrl, PostCategory.of(categoryCode).getName(), createdAt);
    }

    // used
    public PostDetailResponse toPostDetailResponse(Post post, Member leader, boolean isLiked, boolean isAlreadyApplied){
        return PostDetailResponse.builder()
                .postId(post.getId())
                .imageUrl(post.getImageUrl())
                .title(post.getTitle())
                .description(post.getDescription())
                .startDate(post.getStartDate())
                .endDate(post.getEndDate())
                .region(post.getRegion())
                .viewCount(post.getViewCount())
                .status(PostStatus.of(post.getStatusCode()).getName())
                .category(PostCategory.of(post.getCategoryCode()).getName())
                .leader(
                        new PostDetailResponse.MemberDto(
                                leader.getId(),
                                leader.getNickName(),
                                leader.getProfileImageUrl(),
                                leader.getAddress(),
                                new PositionAndColor(
                                        Position.of(PositionParser.parse(leader.getPositionCode(), "-")[0]).getName(),
                                        Position.getBasePosition(PositionParser.parse(leader.getPositionCode(), "-")[0]).getCode()
                                )
                        )
                )
                .onlineInfo(OnlineStatus.of(post.getOnlineCode()).getName())
                .createdAt(post.getCreatedDate())
                .modifiedAt(post.getLastModifiedDate())
                .isLiked(isLiked)
                .likeCount(post.getLikeCount())
                .isAlreadyApplied(isAlreadyApplied)
                .build();
    }

    public PostSimpleResponse toPostSimpleResponse(Post post, List<PositionAndColor> positions) {
        return PostSimpleResponse.builder()
                .postId(post.getId())
                .imageUrl(post.getImageUrl())
                .title(post.getTitle())
                .startDate(post.getStartDate())
                .endDate(post.getEndDate())
                .region(post.getRegion())
                .createdAt(post.getCreatedDate())
                .modifiedAt(post.getLastModifiedDate())
                .positions(positions)
                .likeCount(post.getLikeCount())
                .build();
    }

    public PostDeleteResponse toPostDeleteResponse(Long postId) {
        return new PostDeleteResponse(postId);
    }

    public TeamMemberResponse toTeamMemberResponse(List<Apply> applies) {
        var response = new TeamMemberResponse();

        for (var apply : applies) {
            Member member = apply.getMember();
            response.getTeamMembers().add(
                    new TeamMemberResponse.TeamMember(
                            member.getId(),
                            member.getNickName(),
                            member.getProfileImageUrl(),
                            member.getAddress(),
                            new PositionAndColor(
                                    Position.of(apply.getRecruitingPosition().getPositionCode()).getName(),
                                    Position.getBasePosition(apply.getRecruitingPosition().getPositionCode()).getCode()
                            )
                    )
            );
        }

        return response;
    }

    public MyBudiProjectResponse toMyBudiProjectResponse(List<Apply> applies, List<Post> recruitedPosts) {
        var participatedPostResponse = new ArrayList<MyBudiProjectResponse.ProjectSimple>();
        for(var apply : applies){
            Post post = apply.getPost();

            String appliedStatus = AppliedProjectStatus.makeAppliedProjectStatus(apply.getApplyStatusCode(), apply.getPost().getEndDate());

            participatedPostResponse.add(
                    MyBudiProjectResponse.ProjectSimple.builder()
                            .id(post.getId())
                            .imageUrl(post.getImageUrl())
                            .title(post.getTitle())
                            .startDate(post.getStartDate())
                            .endDate(post.getEndDate())
                            .onlineInfo(OnlineStatus.of(post.getOnlineCode()).getName())
                            .category(PostCategory.of(post.getCategoryCode()).getName())
                            .likeCount(post.getLikeCount())
                            .appliedStatus(appliedStatus)
                            .build()
            );
        }

        var recruitedPostResponse = new ArrayList<MyBudiProjectResponse.ProjectSimple>();
        for(var post : recruitedPosts){
            recruitedPostResponse.add(
                    MyBudiProjectResponse.ProjectSimple.builder()
                            .id(post.getId())
                            .imageUrl(post.getImageUrl())
                            .title(post.getTitle())
                            .startDate(post.getStartDate())
                            .endDate(post.getEndDate())
                            .onlineInfo(OnlineStatus.of(post.getOnlineCode()).getName())
                            .category(PostCategory.of(post.getCategoryCode()).getName())
                            .likeCount(post.getLikeCount())
                            .build()
            );
        }

        return new MyBudiProjectResponse(participatedPostResponse, recruitedPostResponse);
    }
}
