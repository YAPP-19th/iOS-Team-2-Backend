package com.yapp.project.likepost.repository;

import com.yapp.project.likepost.entity.LikePost;
import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    @Override
    <S extends LikePost> S save(S entity);

    boolean existsByMemberAndPost(Member member, Post post);

    Optional<LikePost> findByMemberAndPost(Member member, Post post);

    List<LikePost> findAllByMember(Member member);
}
