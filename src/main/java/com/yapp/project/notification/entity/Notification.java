package com.yapp.project.notification.entity;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.IllegalRequestException;
import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.Post;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "NOTIFICATION_IX01", columnList = "notification_receiver_id")
})
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "notification_receiver_id", referencedColumnName = "member_id")
    private Member receiver;

    @Column(name = "notification_title", nullable = false)
    private String title;

    @Column(name = "notification_body", nullable = false)
    private String body;

    @Column(name = "notification_is_read", nullable = false)
    private Boolean isRead;

    @Column(name = "notification_date", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime date;

    @Column(name = "notification_code", nullable = false)
    private Integer code;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "notification_post_id", referencedColumnName = "post_id")
    private Post post;

    public void updateIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public void validateMemberOrElseThrow(long receiverId) {
        if(this.receiver.getId().longValue() != receiverId){
            throw new IllegalRequestException(ExceptionMessage.ACCESS_DENIED);
        }
    }
}
