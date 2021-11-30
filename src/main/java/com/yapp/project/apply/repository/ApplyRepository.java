package com.yapp.project.apply.repository;

import com.yapp.project.apply.entity.Apply;
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

    List<Apply> findAllByPost(Post post);

    long countByRecruitingPosition(RecruitingPosition recruitingPosition);

    long countByRecruitingPositionAAndApplyStatusCode(RecruitingPosition recruitingPosition, int applyStatusCode);
}
