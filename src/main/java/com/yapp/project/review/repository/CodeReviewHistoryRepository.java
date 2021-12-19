package com.yapp.project.review.repository;

import com.yapp.project.review.entity.CodeReviewHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CodeReviewHistoryRepository extends JpaRepository<CodeReviewHistory, Long> {
    List<CodeReviewHistory> findAllByTargetMemberId(Long targetMemberId);

    @Query("SELECT c.reviewCode, count(c.reviewCode) as cnt " +
            " FROM CodeReviewHistory c " +
            "WHERE c.targetMember.id = :targetId " +
            "GROUP BY c.reviewCode " +
            "ORDER BY c.reviewCode, cnt desc ")
    List<CodeReviewHistory> findALLByTargetMemberIdOrderByCount(@Param("targetId") Long targetId);
}
