package com.yapp.project.member.entity;

import com.yapp.project.common.entity.BaseEntity;
import com.yapp.project.post.entity.TeamMember;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id", unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_member_id", referencedColumnName = "team_member_id")
    private TeamMember teamMember;

    // TODO: post 도메인과 관련된 부분만 임시 구현한 상태
}
