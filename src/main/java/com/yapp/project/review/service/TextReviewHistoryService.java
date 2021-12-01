package com.yapp.project.review.service;

import com.yapp.project.common.exception.ExceptionMessage;
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

@Service
@RequiredArgsConstructor
public class TextReviewHistoryService {
    private final TextReviewHistoryRepository textReviewHistoryRepository;
    private final TextReviewHistoryConverter converter;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public void create(Long reviewerId, TextReviewCreateRequest request){
        Member reviewer = memberRepository.findById(reviewerId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        Member targetMember = memberRepository.findById(request.getTargetMemberId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID));

        var textReviewHistory = converter.toEntity(reviewer, targetMember, post, request.getTitle(), request.getContent());
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
