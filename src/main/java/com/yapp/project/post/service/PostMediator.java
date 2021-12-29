package com.yapp.project.post.service;

import com.yapp.project.apply.repository.ApplyRepository;
import com.yapp.project.likepost.repository.LikePostRepository;
import com.yapp.project.post.entity.Post;
import com.yapp.project.post.repository.RecruitingPositionRepository;
import com.yapp.project.review.repository.CodeReviewHistoryRepository;
import com.yapp.project.review.repository.TextReviewHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PostMediator {
    private final RecruitingPositionRepository recruitingPositionRepository;
    private final CodeReviewHistoryRepository codeReviewHistoryRepository;
    private final TextReviewHistoryRepository textReviewHistoryRepository;
    private final LikePostRepository likePostRepository;
    private final ApplyRepository applyRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    void deleteCascade(Post post) {
        recruitingPositionRepository.deleteAllByPost(post);
        codeReviewHistoryRepository.deleteAllByPost(post);
        textReviewHistoryRepository.deleteAllByPost(post);
        likePostRepository.deleteAllByPost(post);
        applyRepository.deleteAllByPost(post);
    }
}
