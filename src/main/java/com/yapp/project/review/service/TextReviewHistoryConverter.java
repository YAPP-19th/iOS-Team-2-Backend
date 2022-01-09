package com.yapp.project.review.service;

import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.Post;
import com.yapp.project.review.dto.response.TextReviewSimpleResponse;
import com.yapp.project.review.entity.TextReviewHistory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TextReviewHistoryConverter {
    public TextReviewHistory toEntity(Member reviewer, Member targetMember, Post post, String title, String content) {
        return TextReviewHistory.builder()
                .reviewer(reviewer)
                .targetMember(targetMember)
                .post(post)
                .title(title)
                .content(content)
                .build();
    }

    public TextReviewSimpleResponse toTextReviewSimpleResponse(TextReviewHistory textReviewHistory) {
        return new TextReviewSimpleResponse(
                textReviewHistory.getPost().getId(),
                textReviewHistory.getReviewer().getId(),
                textReviewHistory.getTitle(),
                textReviewHistory.getContent()
        );
    }

    public List<TextReviewSimpleResponse> toTextReviewSimpleResponses(List<TextReviewHistory> textReviews) {
        List<TextReviewSimpleResponse> result = new ArrayList<>(textReviews.size());

        for (var textReview : textReviews) {
            result.add(toTextReviewSimpleResponse(textReview));
        }

        return result;
    }
}
