package com.yapp.project.notification.service;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.notification.dto.NotificationResponse;
import com.yapp.project.notification.entity.Notification;
import com.yapp.project.notification.repository.NotificationRepository;
import com.yapp.project.notification.repository.UnreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UnreadRepository unreadRepository;
    private final NotificationConverter converter;

    @Transactional
    public void save() {

    }

    @Transactional(readOnly = true)
    public Page<NotificationResponse> findAllByMember(Pageable pageable, long receiverId) {
        Page<Notification> notiPage = notificationRepository.findAllByReceiverId(pageable, receiverId);

        return notiPage.map(n -> converter.toNotificationResponse(n));
    }

    @Transactional
    public void toggleReadStatus(long notificationId, long receiverId) { // 읽음 처리
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.BAD_REQUEST));

        notification.validateMemberOrElseThrow(receiverId);

        notification.updateIsRead(true);
    }

}
