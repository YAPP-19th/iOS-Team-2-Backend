package com.yapp.project.review.service;

import com.yapp.project.member.entity.Member;
import com.yapp.project.review.dto.response.CodeReviewCountResponse;
import com.yapp.project.review.entity.CodeReviewHistory;
import com.yapp.project.review.entity.value.ReviewCode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CodeReviewHistoryConverter {
    public CodeReviewHistory toEntity(Member reviewer, Member targetmember, String selectedReview){
        return CodeReviewHistory.builder()
                .reviewer(reviewer)
                .targetMember(targetmember)
                .reviewCode(ReviewCode.of(selectedReview).getReviewCode())
                .build();
    }

    public CodeReviewCountResponse toCodeReviewCountResponse(List<CodeReviewHistory> codeReviews) {
        CodeReviewCountResponse response = new CodeReviewCountResponse();
        var positiveMap = response.getPositives();
        var negativeMap = response.getPositives();

        for(var codeReview : codeReviews){
            int reviewCode = codeReview.getReviewCode();
            String reviewName = ReviewCode.of(reviewCode).getReviewName();

            if(reviewCode > 0){
                positiveMap.put(reviewName, positiveMap.getOrDefault(reviewName, 0) + 1);
            }else{
                negativeMap.put(reviewName, negativeMap.getOrDefault(reviewName, 0) + 1);
            }
        }

        return response;
    }
}
