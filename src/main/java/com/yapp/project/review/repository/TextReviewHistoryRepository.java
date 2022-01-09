package com.yapp.project.review.repository;

import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.Post;
import com.yapp.project.review.entity.TextReviewHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TextReviewHistoryRepository extends JpaRepository<TextReviewHistory, Long> {
    @Override
    <S extends TextReviewHistory> S save(S entity);

    Page<TextReviewHistory> findAllByTargetMember(Member targetMember, Pageable pageable);

    Page<TextReviewHistory> findAllByTargetMember_Id(Long targetId, Pageable pageable);

    boolean existsByReviewerAndTargetMemberAndPost(Member reviewer, Member targetMember, Post post);

    void deleteAllByPost(Post post);

    List<TextReviewHistory> findAllByTargetMemberIdAndPostId(long targetMemberId, long postId);

    boolean existsByReviewerIdAndTargetMemberIdAndPostId(long reviewerId, long targetMemberId, long postId);
}
