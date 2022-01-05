package com.yapp.project.notification.entity;

import com.yapp.project.member.entity.Member;
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

    public void updateIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}
