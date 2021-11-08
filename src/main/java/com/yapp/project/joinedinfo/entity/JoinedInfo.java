package com.yapp.project.joinedinfo.entity;

import com.yapp.project.common.entity.BaseEntity;
import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.RecruitingPosition;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "joined_info")
public class JoinedInfo extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "joined_info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "joined_info_recruiting_position_id", referencedColumnName = "recruiting_position_id")
    private RecruitingPosition recruitingPosition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "joined_info_member_id", referencedColumnName = "member_id")
    private Member member;
}
