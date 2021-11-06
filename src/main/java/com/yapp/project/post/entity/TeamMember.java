package com.yapp.project.post.entity;

import com.yapp.project.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "team_member")
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_member_id", unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_member_post_id", referencedColumnName = "post_id", nullable = false)
    private Post post;

    @Column(name = "team_member_position_code")
    private int positionCode;

    @Column(name = "team_member_recruiting_number")
    private int recruitingNumber;

}
