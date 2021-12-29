package com.yapp.project.review.entity;

import com.yapp.project.common.entity.DeletableEntity;
import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.Post;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE code_review_history SET is_deleted = true WHERE code_review_history_id=?")
@Where(clause = "is_deleted = false")
public class CodeReviewHistory extends DeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_review_history_id")
    private Long id;

    @Column(name = "code_review_history_code", nullable = false)
    private int reviewCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "code_review_history_reviewer_id", referencedColumnName = "member_id")
    private Member reviewer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "code_review_history_target_member_id", referencedColumnName = "member_id")
    private Member targetMember;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "code_review_history_post_id", referencedColumnName = "post_id")
    private Post post;
}
