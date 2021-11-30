package com.yapp.project.review.service;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.repository.MemberRepository;
import com.yapp.project.review.dto.request.CodeReviewInsertRequest;
import com.yapp.project.review.dto.response.CodeReviewListResponse;
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

        // TODO: apply의 status를 비교해 참여 확정된 인원인지 알아보가

        List<String> selectedReviews = request.getSelectedReviews();
        for(var selectedReview : selectedReviews){
            var codeReviewHistory = converter.toEntity(reviewer, targetMember, selectedReview);
            codeReviewHistoryRepossitory.save(codeReviewHistory);
        }
    }
}
