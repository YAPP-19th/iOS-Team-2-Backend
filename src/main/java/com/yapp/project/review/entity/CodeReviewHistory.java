package com.yapp.project.review.entity;

import com.yapp.project.common.entity.BaseEntity;
import com.yapp.project.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CodeReviewHistory extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "code_review_history_id")
    private Long id;

    @Column(name = "code_review_history_code")
    private Integer reviewCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_review_history_reviewer_id", referencedColumnName = "member_id")
    private Member reviewer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "code_review_history_target_member_id", referencedColumnName = "member_id")
    private Member targetMember;
}
