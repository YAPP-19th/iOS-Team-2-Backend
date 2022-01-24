package com.yapp.project.post.repository;

import com.yapp.project.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
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

    Page<Post> findByTitleIgnoreCaseContains(Pageable pageable, String title);

    List<Post> findAllByOwnerId(long ownerId);

    @Modifying
    @Query(value = "DELETE FROM Post p WHERE p.isDeleted = true and p.lastModifiedDate < :baseDeletionTime")
    void deleteAllExpired(LocalDateTime baseDeletionTime);
}
