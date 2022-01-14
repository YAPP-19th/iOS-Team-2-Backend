package com.yapp.project.member.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "LIKEMEMBER_IX01", columnList = "like_member_from_member_id, like_member_to_member_id")
})
public class LikeMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "like_member_from_member_id", referencedColumnName = "member_id")
    private Member fromMember;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "like_member_to_member_id", referencedColumnName = "member_id")
    private Member toMember;

    public LikeMember(Member fromMember, Member toMember){
        this.fromMember = fromMember;
        this.toMember = toMember;
    }
}
