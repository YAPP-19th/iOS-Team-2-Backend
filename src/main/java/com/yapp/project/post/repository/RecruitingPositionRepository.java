package com.yapp.project.post.repository;

import com.yapp.project.post.entity.RecruitingPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitingPositionRepository extends JpaRepository<RecruitingPosition, Long> {
    @Override
    <S extends RecruitingPosition> S save(S entity);

    List<RecruitingPosition> findAllByPostId(Long postId);
}
