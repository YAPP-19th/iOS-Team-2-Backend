package com.yapp.project.apply.repository;

import com.yapp.project.apply.entity.Apply;
import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.Post;
import com.yapp.project.post.entity.RecruitingPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
    @Override
    boolean existsById(Long id);

    @Override
    Optional<Apply> findById(Long id);

    List<Apply> findAllByPostAndApplyStatusCode(Post post, int applyStatusCode);

    long countByRecruitingPosition(RecruitingPosition recruitingPosition);

    long countByRecruitingPositionAndApplyStatusCode(RecruitingPosition recruitingPosition, int applyStatusCode);

    boolean existsByMemberAndPost(Member member, Post post);

    Optional<Apply> findByMemberAndPost(Member member, Post post);
}
