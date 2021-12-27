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
import com.yapp.project.review.dto.request.TextReviewCreateRequest;
import com.yapp.project.review.dto.response.TextReviewSimpleResponse;
import com.yapp.project.review.entity.TextReviewHistory;
import com.yapp.project.review.repository.TextReviewHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TextReviewHistoryService {
    private final TextReviewHistoryRepository textReviewHistoryRepository;
    private final TextReviewHistoryConverter converter;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ApplyRepository applyRepository;

    @Transactional
    public void create(long reviewerId, long revieweeId, long postId, TextReviewCreateRequest request) {
        Member reviewer = memberRepository.findById(reviewerId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));
        Member reviewee = memberRepository.findById(revieweeId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        if (reviewer.getId().longValue() == reviewee.getId().longValue()) {
            throw new IllegalRequestException(ExceptionMessage.NO_SELF_REVIEW);
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID));

        Optional<Apply> applyOptional = Optional.empty();
        if (post.validateLeader(reviewer)) { // 리뷰어가 프로젝트 리더인 경우
            applyOptional = applyRepository.findByMemberAndPost(reviewee, post);
        } else { // 리뷰어가 팀원인 경우
            applyOptional = applyRepository.findByMemberAndPost(reviewer, post);
        }

        applyOptional.orElseThrow(() -> new NotFoundException(ExceptionMessage.ILLEGAL_TARGETMEMBER));

        ApplyStatus.validateApprovedCodeOrElseThrow(applyOptional.get().getApplyStatusCode());

        if (textReviewHistoryRepository.existsByReviewerAndTargetMemberAndPost(reviewer, reviewee, post)) {
            throw new IllegalRequestException(ExceptionMessage.ALREADY_REVIEWED);
        }

        var textReviewHistory = converter.toEntity(reviewer, reviewee, post, request.getTitle(), request.getContent());
        textReviewHistoryRepository.save(textReviewHistory);
    }

    @Transactional(readOnly = true)
    public Page<TextReviewSimpleResponse> findAllByPages(Long targetMemberId, Pageable pageable) {
        Member targetMember = memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        Page<TextReviewHistory> allByMember = textReviewHistoryRepository.findAllByTargetMember(targetMember, pageable);

        return allByMember.map(t -> converter.toTextReviewSimpleResponse(t));
    }
}
