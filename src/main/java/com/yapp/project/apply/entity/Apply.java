package com.yapp.project.apply.entity;

import com.yapp.project.common.entity.BaseEntity;
import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.RecruitingPosition;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Apply extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "apply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "apply_member_id", referencedColumnName = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "apply_recruiting_position_id", referencedColumnName = "recruiting_position_id")
    private RecruitingPosition recruitingPosition;

    @Column(name = "apply_status_code")
    private Integer applyStatusCode;

    public void updateApplyStatusCode(int applyStatusCode){
        this.applyStatusCode = applyStatusCode;
    }
}
