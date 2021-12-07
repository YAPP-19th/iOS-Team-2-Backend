package com.yapp.project.review.service;

import com.yapp.project.apply.repository.ApplyRepository;
import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.IllegalRequestException;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.repository.MemberRepository;
import com.yapp.project.post.entity.Post;
import com.yapp.project.post.repository.PostRepository;
import com.yapp.project.review.dto.request.CodeReviewInsertRequest;
import com.yapp.project.review.dto.response.CodeReviewCountResponse;
import com.yapp.project.review.dto.response.CodeReviewListResponse;
import com.yapp.project.review.entity.CodeReviewHistory;
import com.yapp.project.review.entity.value.ReviewCode;
import com.yapp.project.review.repository.CodeReviewHistoryRepossitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeReviewHistoryService {
    private final CodeReviewHistoryRepossitory codeReviewHistoryRepossitory;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ApplyRepository applyRepository;
    private final CodeReviewHistoryConverter converter;

    public CodeReviewListResponse findAllReviews(){
        return new CodeReviewListResponse(
                ReviewCode.getAllPositiveReviewNames(),
                ReviewCode.getAllNegativeReviewNames()
        );
    }

    @Transactional
    public void create(Long fromMemberId, Long targetMemberId, CodeReviewInsertRequest request) {
        Member reviewer = memberRepository.findById(fromMemberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));
        Member targetMember = memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID));

        if(!post.validateLeader(targetMember)){
            throw new IllegalRequestException(ExceptionMessage.POST_ID_AND_MEMBER_ID_MISMATCH);
        }

        if(applyRepository.existsByMemberAndPost(reviewer, post)){
            throw new IllegalRequestException(ExceptionMessage.ALREADY_REVIEWED);
        }

        if(targetMember.isSameMember(reviewer)){
            throw new IllegalRequestException(ExceptionMessage.NO_SELF_REVIEW);
        }

        List<String> selectedReviews = request.getSelectedReviews();
        for(var selectedReview : selectedReviews){
            var codeReviewHistory = converter.toEntity(reviewer, targetMember, selectedReview);
            codeReviewHistoryRepossitory.save(codeReviewHistory);
        }
    }

    @Transactional(readOnly = true)
    public CodeReviewCountResponse findAllByMember(Long targetMemberId) {
        memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        List<CodeReviewHistory> codeReviews = codeReviewHistoryRepossitory.findAllByTargetMemberId(targetMemberId);

        return converter.toCodeReviewCountResponse(codeReviews);
    }
}

