package com.yapp.project.review.repository;

import com.yapp.project.review.entity.CodeReviewHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeReviewHistoryRepossitory extends JpaRepository<CodeReviewHistory, Long> {
    List<CodeReviewHistory> findAllByTargetMemberId(Long targetMemberId);
}
