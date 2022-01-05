package com.yapp.project.notification.repository;

import com.yapp.project.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Override
    <S extends Notification> S save(S entity);

    @Override
    Optional<Notification> findById(Long id);

    Page<Notification> findAllByReceiverId(Pageable pageable, long receiverId);
}
