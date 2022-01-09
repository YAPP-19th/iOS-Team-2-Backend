package com.yapp.project.review.service;

import com.yapp.project.apply.entity.Apply;
import com.yapp.project.apply.entity.value.ApplyStatus;
import com.yapp.project.apply.repository.ApplyRepository;
import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.IllegalRequestException;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.repository.MemberRepository;
import com.yapp.project.post.entity.Post;
import com.yapp.project.post.repository.PostRepository;
import com.yapp.project.review.dto.response.CodeReviewCountResponse;
import com.yapp.project.review.entity.CodeReviewHistory;
import com.yapp.project.review.entity.value.ReviewCode;
import com.yapp.project.review.repository.CodeReviewHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeReviewHistoryService {
    private final CodeReviewHistoryRepository codeReviewHistoryRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ApplyRepository applyRepository;
    private final CodeReviewHistoryConverter converter;

    @Transactional
    public void create(long reviewerId, long revieweeId, long postId, List<String> selectedReviews) {
        Member reviewer = memberRepository.findById(reviewerId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));
        Member reviewee = memberRepository.findById(revieweeId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        if (reviewer.getId().longValue() == reviewee.getId().longValue()) {
            throw new IllegalRequestException(ExceptionMessage.NO_SELF_REVIEW);
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID));

        if (post.getOwner().getId().longValue() == reviewee.getId().longValue()) { // 타겟이 프로젝트 리더인 경우
            Apply reviewerApply = applyRepository.findByMemberAndPost(reviewer, post)
                    .orElseThrow(() -> new NotFoundException(ExceptionMessage.ILLEGAL_TARGETMEMBER));

            ApplyStatus.validateApprovedCodeOrElseThrow(reviewerApply.getApplyStatusCode());
        } else {  // 타겟이 팀원인 경우
            if (reviewer.getId().longValue() == post.getOwner().getId().longValue()) { // 리뷰어가 프로젝트 리더인 경우
                Apply revieweeApply = applyRepository.findByMemberAndPost(reviewee, post)
                        .orElseThrow(() -> new NotFoundException(ExceptionMessage.ILLEGAL_TARGETMEMBER));

                ApplyStatus.validateApprovedCodeOrElseThrow(revieweeApply.getApplyStatusCode());
            } else { // 참여자가 또 다른 참여자에게 리뷰를 남기는 경우
                Apply reviewerApply = applyRepository.findByMemberAndPost(reviewer, post)
                        .orElseThrow(() -> new NotFoundException(ExceptionMessage.ILLEGAL_TARGETMEMBER));
                Apply revieweeApply = applyRepository.findByMemberAndPost(reviewer, post)
                        .orElseThrow(() -> new NotFoundException(ExceptionMessage.ILLEGAL_TARGETMEMBER));

                ApplyStatus.validateApprovedCodeOrElseThrow(reviewerApply.getApplyStatusCode());
                ApplyStatus.validateApprovedCodeOrElseThrow(revieweeApply.getApplyStatusCode());
            }
        }

        if (codeReviewHistoryRepository.existsByReviewerAndTargetMemberAndPost(reviewer, reviewee, post)) {
            throw new IllegalRequestException(ExceptionMessage.ALREADY_REVIEWED);
        }

        for (var selectedReview : selectedReviews) { // TODO: 선택된 리뷰 count로 변경 (reviewer가 문제가 됨)
            ReviewCode.validateIsExistReviewOrElseThrow(selectedReview);

            var codeReviewHistory = converter.toEntity(reviewer, reviewee, selectedReview, post);
            codeReviewHistoryRepository.save(codeReviewHistory);
        }

        // TODO: 상대방의 레벨 검사 로직
    }

    @Transactional(readOnly = true)
    public CodeReviewCountResponse findAllByMember(long targetMemberId) {
        memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        List<CodeReviewHistory> codeReviews = codeReviewHistoryRepository.findAllByTargetMemberId(targetMemberId);

        return converter.toCodeReviewCountResponse(codeReviews);
    }

    @Transactional(readOnly = true)
    public CodeReviewCountResponse findAllByMemberAndPost(long currentMemberId, long postId) {
        List<CodeReviewHistory> codeReviews = codeReviewHistoryRepository.findAllByTargetMemberIdAndPostId(currentMemberId, postId);

        return converter.toCodeReviewCountResponse(codeReviews);
    }
}

