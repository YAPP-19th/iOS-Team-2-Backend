package com.yapp.project.notification.repository;

import com.yapp.project.notification.entity.Unread;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnreadRepository extends JpaRepository<Unread, Long> {
    @Override
    <S extends Unread> S save(S entity);

    Optional<Unread> findByMemberId(long memberId);
}
