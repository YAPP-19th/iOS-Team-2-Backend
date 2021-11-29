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
public class TextReviewHistory extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "text_review_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "text_review_history_reviewer_id", referencedColumnName = "member_id")
    private Member reviewer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "text_review_history_target_member_id", referencedColumnName = "member_id")
    private Member targetMember;

    @Column(name = "text_review_history_content", length = 1000)
    private String content;

    @Column(name = "text_review_history_title")
    private String title;
}
