package com.yapp.project.member.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LikeMember {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "like_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "like_member_from_member_id", referencedColumnName = "member_id")
    private Member fromMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "like_member_to_member_id", referencedColumnName = "member_id")
    private Member toMember;

    public LikeMember(Member fromMember, Member toMember){
        this.fromMember = fromMember;
        this.toMember = toMember;
    }
}
