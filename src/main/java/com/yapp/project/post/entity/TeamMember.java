package com.yapp.project.post.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "team_member")
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_member_post_id", referencedColumnName = "post_id")
    private Post post;

    @Column(name = "team_member_position_code")
    private int positionCode;

    @Column(name = "team_member_skill_code")
    private int skillCode;

    @Column(name = "team_member_recruiting_number")
    private int recruitingNumber;

}
