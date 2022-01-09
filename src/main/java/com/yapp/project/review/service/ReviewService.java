package com.yapp.project.review.service;

import com.yapp.project.apply.entity.Apply;
import com.yapp.project.apply.entity.value.ApplyStatus;
import com.yapp.project.apply.repository.ApplyRepository;
import com.yapp.project.common.dto.PositionAndColor;
import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.IllegalRequestException;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.common.util.PositionParser;
import com.yapp.project.common.value.Position;
import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.Post;
import com.yapp.project.post.repository.PostRepository;
import com.yapp.project.review.dto.response.TeamResponse;
import com.yapp.project.review.repository.TextReviewHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final PostRepository postRepository;
    private final ApplyRepository applyRepository;
    private final TextReviewHistoryRepository textReviewHistoryRepository;

    @Transactional(readOnly = true)
    public List<TeamResponse> getAllTeamMembers(long postId, long currentMemberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID));

        List<Apply> applies = applyRepository.findAllByPostAndApplyStatusCode(post, ApplyStatus.APPROVAL_FOR_PARTICIPATION.getCode());

        boolean isCurrentMemberParticipatedInThisProject = post.getOwner().getId().longValue() == currentMemberId;

        if (!isCurrentMemberParticipatedInThisProject) {
            isCurrentMemberParticipatedInThisProject = isCurrentMemberApproved(applies, currentMemberId);
        }

        if (!isCurrentMemberParticipatedInThisProject) {
            throw new IllegalRequestException(ExceptionMessage.ACCESS_DENIED);
        }

        List<TeamResponse> result = new ArrayList<>(applies.size());
        boolean isAlreadyReviewedFromCurrentMember = false;

        if (post.getOwner().getId().longValue() != currentMemberId) { // 현재 멤버는 빼고 담아야 함
            isAlreadyReviewedFromCurrentMember = textReviewHistoryRepository.existsByReviewerIdAndTargetMemberIdAndPostId(currentMemberId, post.getOwner().getId(), post.getId());
            result.add(toTeamResponse(post.getOwner(), isAlreadyReviewedFromCurrentMember));
        }

        for (var apply : applies) {
            var applyer = apply.getMember();

            if (applyer.getId().longValue() == currentMemberId) continue;

            isAlreadyReviewedFromCurrentMember = textReviewHistoryRepository.existsByReviewerIdAndTargetMemberIdAndPostId(currentMemberId, applyer.getId(), post.getId());
            result.add(toTeamResponse(applyer, isAlreadyReviewedFromCurrentMember));
        }

        return result;
    }

    private boolean isCurrentMemberApproved(List<Apply> applies, long currentMemberId) {
        for (var apply : applies) {
            if (apply.getMember().getId().longValue() == currentMemberId)
                return true;
        }

        return false;
    }

    private TeamResponse toTeamResponse(Member member, boolean isAlreadyReviewedFromCurrentMember) {
        return TeamResponse.builder()
                .id(member.getId())
                .nickName(member.getNickName())
                .profileImageUrl(member.getProfileImageUrl())
                .address(member.getAddress())
                .position(
                        new PositionAndColor(
                                Position.of(PositionParser.parse(member.getPositionCode(), "-")[0]).getName(),
                                Position.getBasePosition(PositionParser.parse(member.getPositionCode(), "-")[0]).getCode()
                        )
                )
                .isAlreadyReviewedFromCurrentMember(isAlreadyReviewedFromCurrentMember)
                .build();
    }
}
