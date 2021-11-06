package com.yapp.project.likepost.entity;

import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.Post;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LikePost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "like_post_id", unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "like_post_member_id", referencedColumnName = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "like_post_post_id", referencedColumnName = "post_id", nullable = false)
    private Post post;
}
