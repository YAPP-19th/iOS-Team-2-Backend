package com.yapp.project.apply.entity;

import com.yapp.project.common.entity.DeletableEntity;
import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.Post;
import com.yapp.project.post.entity.RecruitingPosition;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE apply SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
public class Apply extends DeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_member_id", referencedColumnName = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_recruiting_position_id", referencedColumnName = "recruiting_position_id")
    private RecruitingPosition recruitingPosition;

    @Column(name = "apply_status_code")
    private Integer applyStatusCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_post_id", referencedColumnName = "post_id")
    private Post post;

    public void updateApplyStatusCode(int applyStatusCode){
        this.applyStatusCode = applyStatusCode;
    }
}
