package com.yapp.project.notification.service;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.repository.MemberRepository;
import com.yapp.project.notification.dto.NotificationResponse;
import com.yapp.project.notification.entity.Notification;
import com.yapp.project.notification.entity.Unread;
import com.yapp.project.notification.repository.NotificationRepository;
import com.yapp.project.notification.repository.UnreadRepository;
import com.yapp.project.post.entity.Post;
import com.yapp.project.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UnreadRepository unreadRepository;
    private final NotificationConverter converter;

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public void save(long receiverId, String title, String body, int notificationTypeCode, long relatedPostId) {
        Member receiver = memberRepository.findById(receiverId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.MEMBER_NOT_FOUND));

        Post relatedPost = postRepository.findById(relatedPostId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID));

        Notification notification = converter.toEntity(receiver, title, body, notificationTypeCode, relatedPost);
        notificationRepository.save(notification);

        Optional<Unread> unreadOpt = unreadRepository.findByMemberId(receiverId);
        if (unreadOpt.isPresent()) {
            unreadOpt.get().addCount();
        } else {
            unreadRepository.save(new Unread(receiver, 1));
        }
    }

    @Transactional(readOnly = true)
    public Page<NotificationResponse> findAllByMember(Pageable pageable, long receiverId) {
        Page<Notification> notiPage = notificationRepository.findAllByReceiverId(pageable, receiverId);

        return notiPage.map(n -> converter.toNotificationResponse(n));
    }

    @Transactional
    public void toggleReadStatus(long notificationId, long receiverId) { // ?????? ??????
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.BAD_REQUEST));

        notification.validateMemberOrElseThrow(receiverId);

        notification.updateIsRead(true);

        Unread unread = unreadRepository.findByMemberId(receiverId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.MEMBER_NOT_FOUND));

        unread.substractCount();
    }

    @Transactional(readOnly = true)
    public int getUnreadCount(long currentMemberId) {
        Optional<Unread> unreadOpt = unreadRepository.findByMemberId(currentMemberId);

        if (unreadOpt.isEmpty()) {
            return 0;
        }

        return unreadOpt.get().getCount();
    }

}
