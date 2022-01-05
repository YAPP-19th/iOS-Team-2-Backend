package com.yapp.project.notification.service;

import com.yapp.project.member.entity.Member;
import com.yapp.project.notification.dto.NotificationResponse;
import com.yapp.project.notification.entity.Notification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificationConverter {
    public NotificationResponse toNotificationResponse(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getTitle(),
                notification.getBody(),
                notification.getIsRead(),
                notification.getDate()
        );
    }

    public Notification toEntity(Member receiver, String title, String body) {
        return Notification.builder()
                .receiver(receiver)
                .title(title)
                .body(body)
                .isRead(false)
                .date(LocalDateTime.now())
                .build();
    }
}
