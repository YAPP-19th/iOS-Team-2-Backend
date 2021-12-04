package com.yapp.project.post.repository;

import com.yapp.project.post.entity.Post;
import com.yapp.project.post.entity.RecruitingPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecruitingPositionRepository extends JpaRepository<RecruitingPosition, Long> {
    @Override
    <S extends RecruitingPosition> S save(S entity);

    List<RecruitingPosition> findAllByPostId(Long postId);

//    @Query(value = "select distinct r from RecruitingPosition r join fetch r.post where r.positionCode = :positionCode")
//    Page<RecruitingPosition> findAllByPositionCode(@Param("positionCode") int positionCode, Pageable pageable);

    Page<RecruitingPosition> findAllByRootPositionCode(int rootPositionCode, Pageable pageable);

    void deleteAllByPost(Post post);
}
