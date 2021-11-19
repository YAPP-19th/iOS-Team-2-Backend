package com.yapp.project.post.repository;

import com.yapp.project.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Override
    Page<Post> findAll(Pageable pageable);

    @Override
    <S extends Post> S save(S entity);

    @Override
    Optional<Post> findById(Long id);

    @Override
    boolean existsById(Long id);
}
