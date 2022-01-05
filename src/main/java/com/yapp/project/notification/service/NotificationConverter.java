package com.yapp.project.notification.service;

import com.yapp.project.notification.dto.NotificationResponse;
import com.yapp.project.notification.entity.Notification;
import org.springframework.stereotype.Component;

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
}
