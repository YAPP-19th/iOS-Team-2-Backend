package com.yapp.project.review.service;

import com.yapp.project.member.entity.Member;
import com.yapp.project.review.entity.CodeReviewHistory;
import com.yapp.project.review.entity.value.ReviewCode;
import org.springframework.stereotype.Component;

@Component
public class CodeReviewHistoryConverter {
    public CodeReviewHistory toEntity(Member reviewer, Member targetmember, String selectedReview){
        return CodeReviewHistory.builder()
                .reviewer(reviewer)
                .targetMember(targetmember)
                .reviewCode(ReviewCode.of(selectedReview).getReviewCode())
                .build();
    }
}
