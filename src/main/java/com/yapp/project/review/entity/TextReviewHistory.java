package com.yapp.project.review.entity;

import com.yapp.project.common.entity.DeletableEntity;
import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.Post;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE text_review_history SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
public class TextReviewHistory extends DeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "text_review_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "text_review_history_reviewer_id", referencedColumnName = "member_id")
    private Member reviewer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "text_review_history_target_member_id", referencedColumnName = "member_id")
    private Member targetMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "text_review_history_post_id", referencedColumnName = "post_id")
    private Post post;

    @Column(name = "text_review_history_content", length = 1000, nullable = false)
    private String content;

    @Column(name = "text_review_history_title", nullable = false)
    private String title;
}
