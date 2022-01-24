package com.yapp.project.review.repository;

import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.Post;
import com.yapp.project.review.dto.response.CodeReviewResponse;
import com.yapp.project.review.entity.CodeReviewHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CodeReviewHistoryRepository extends JpaRepository<CodeReviewHistory, Long> {
    List<CodeReviewHistory> findAllByTargetMemberId(Long targetMemberId);

    @Query("SELECT new com.yapp.project.review.dto.response.CodeReviewResponse(c.reviewCode, count(c.reviewCode), '') " +
            " FROM CodeReviewHistory c " +
            "WHERE c.targetMember.id = :targetId " +
            "GROUP BY c.reviewCode " +
            "ORDER BY 1 desc, 2 desc")
    List<CodeReviewResponse> findALLByTargetMemberIdOrderByCount(@Param("targetId") Long targetId); // 미사용 쿼리메소드 -> 삭제 예정

    boolean existsByReviewerAndTargetMemberAndPost(Member reviewer, Member targetMember, Post post);

    void deleteAllByPost(Post post);

    List<CodeReviewHistory> findAllByTargetMemberIdAndPostId(long targetMemberId, long postId);

    @Modifying
    @Query(value = "DELETE FROM CodeReviewHistory cr WHERE cr.isDeleted = true and cr.lastModifiedDate < :baseDeletionTime")
    void deleteAllExpired(LocalDateTime baseDeletionTime);
}
