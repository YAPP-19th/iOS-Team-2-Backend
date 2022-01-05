package com.yapp.project.notification.repository;

import com.yapp.project.notification.entity.Unread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnreadRepository extends JpaRepository<Unread, Long> {
}
