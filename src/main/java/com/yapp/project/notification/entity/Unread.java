package com.yapp.project.notification.entity;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.IllegalRequestException;
import com.yapp.project.member.entity.Member;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Unread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unread_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "unread_member_id", referencedColumnName = "member_id", unique = true)
    private Member member;

    @Column(name = "unread_count", nullable = false)
    private Integer count;

    public Unread(Member member, Integer count) {
        this.member = member;
        this.count = count;
    }

    public void addCount() {
        this.count++;
    }

    public void substractCount() {
        if (this.count <= 0) {
            throw new IllegalRequestException(ExceptionMessage.UNEXPECTED_EXCEPTIONS);
        }

        this.count--;
    }
}
