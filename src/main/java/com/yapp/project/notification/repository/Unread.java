package com.yapp.project.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Unread extends JpaRepository<Unread, Long> {
}
