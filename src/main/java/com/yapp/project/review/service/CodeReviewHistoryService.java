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

import java.time.LocalDateTime;
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

        if (codeReviewHistoryRepository.existsByReviewerAndTargetMemberAndPost(reviewer, reviewee, post)) {
            throw new IllegalRequestException(ExceptionMessage.ALREADY_REVIEWED);
        }

        if (post.getOwner().getId().longValue() == reviewee.getId().longValue()) { // ????????? ???????????? ????????? ??????
            Apply reviewerApply = applyRepository.findByMemberAndPost(reviewer, post)
                    .orElseThrow(() -> new NotFoundException(ExceptionMessage.ILLEGAL_TARGETMEMBER));

            ApplyStatus.validateApprovedCodeOrElseThrow(reviewerApply.getApplyStatusCode());
        } else {  // ????????? ????????? ??????
            if (reviewer.getId().longValue() == post.getOwner().getId().longValue()) { // ???????????? ???????????? ????????? ??????
                Apply revieweeApply = applyRepository.findByMemberAndPost(reviewee, post)
                        .orElseThrow(() -> new NotFoundException(ExceptionMessage.ILLEGAL_TARGETMEMBER));

                ApplyStatus.validateApprovedCodeOrElseThrow(revieweeApply.getApplyStatusCode());
            } else { // ???????????? ??? ?????? ??????????????? ????????? ????????? ??????
                Apply reviewerApply = applyRepository.findByMemberAndPost(reviewer, post)
                        .orElseThrow(() -> new NotFoundException(ExceptionMessage.ILLEGAL_TARGETMEMBER));
                Apply revieweeApply = applyRepository.findByMemberAndPost(reviewer, post)
                        .orElseThrow(() -> new NotFoundException(ExceptionMessage.ILLEGAL_TARGETMEMBER));

                ApplyStatus.validateApprovedCodeOrElseThrow(reviewerApply.getApplyStatusCode());
                ApplyStatus.validateApprovedCodeOrElseThrow(revieweeApply.getApplyStatusCode());
            }
        }

        int toBeAddedScore = 0;
        for (var selectedReview : selectedReviews) { // TODO: ????????? ?????? count??? ?????? (reviewer??? ????????? ???)
            ReviewCode.validateIsExistReviewOrElseThrow(selectedReview);

            int reviewCode = ReviewCode.of(selectedReview).getCode();
            toBeAddedScore = reviewCode < 0 ? toBeAddedScore - 1 : toBeAddedScore + 1;

            var codeReviewHistory = converter.toEntity(reviewer, reviewee, reviewCode, post);
            codeReviewHistoryRepository.save(codeReviewHistory);
        }

        reviewee.updateReviewScore(reviewee.getReviewScore() + toBeAddedScore);
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

    @Transactional
    public void deleteAllExpiredDate(LocalDateTime baseDeletionTime) {
        codeReviewHistoryRepository.deleteAllExpired(baseDeletionTime);
    }
}

