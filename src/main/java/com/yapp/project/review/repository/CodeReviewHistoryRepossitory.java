package com.yapp.project.review.repository;

import com.yapp.project.review.entity.CodeReviewHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeReviewHistoryRepossitory extends JpaRepository<CodeReviewHistory, Long> {
}
