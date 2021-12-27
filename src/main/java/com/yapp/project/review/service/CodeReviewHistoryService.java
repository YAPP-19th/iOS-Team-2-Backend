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
import com.yapp.project.review.dto.response.CodeReviewListResponse;
import com.yapp.project.review.entity.CodeReviewHistory;
import com.yapp.project.review.entity.value.ReviewCode;
import com.yapp.project.review.repository.CodeReviewHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CodeReviewHistoryService {
    private final CodeReviewHistoryRepository codeReviewHistoryRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ApplyRepository applyRepository;
    private final CodeReviewHistoryConverter converter;

    public CodeReviewListResponse findAllReviews() {
        return new CodeReviewListResponse(
                ReviewCode.getAllPositiveReviewNames(),
                ReviewCode.getAllNegativeReviewNames()
        );
    }

    @Transactional
    public void create(long reviewerId, long revieweeId, long postId, List<String> selectedReviews) {
        Member reviewer = memberRepository.findById(reviewerId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));
        Member reviewee = memberRepository.findById(revieweeId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        if(reviewer.getId().longValue() == reviewee.getId().longValue()){
            throw new IllegalRequestException(ExceptionMessage.NO_SELF_REVIEW);
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID));

        Optional<Apply> applyOptional = Optional.empty();
        if (post.getOwner().getId().longValue() == reviewer.getId().longValue()) { // 리뷰어가 프로젝트 리더인 경우
            applyOptional = applyRepository.findByMemberAndPost(reviewee, post);
        } else { // 리뷰어가 팀원인 경우
            applyOptional = applyRepository.findByMemberAndPost(reviewer, post);
        }

        applyOptional.orElseThrow(() -> new NotFoundException(ExceptionMessage.ILLEGAL_TARGETMEMBER));

        ApplyStatus.validateApprovedCodeOrElseThrow(applyOptional.get().getApplyStatusCode());

        if(codeReviewHistoryRepository.existsByReviewerAndTargetMemberAndPost(reviewer, reviewee, post)){
            throw new IllegalRequestException(ExceptionMessage.ALREADY_REVIEWED);
        }

        for (var selectedReview : selectedReviews) {
            var codeReviewHistory = converter.toEntity(reviewer, reviewee, selectedReview);
            codeReviewHistoryRepository.save(codeReviewHistory);
        }
    }

    @Transactional(readOnly = true)
    public CodeReviewCountResponse findAllByMember(long targetMemberId) {
        memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        List<CodeReviewHistory> codeReviews = codeReviewHistoryRepository.findAllByTargetMemberId(targetMemberId);

        return converter.toCodeReviewCountResponse(codeReviews);
    }
}

