package com.yapp.project.notification.repository;

import com.yapp.project.external.fcm.FcmMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<FcmMessage.Notification, Long> {
}
