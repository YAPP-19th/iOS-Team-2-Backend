package com.yapp.project.likepost.repository;

import com.yapp.project.likepost.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
}
