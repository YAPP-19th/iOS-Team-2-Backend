package com.yapp.project.likepost.entity;

import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.Post;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LikePost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "like_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "like_post_member_id", referencedColumnName = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "like_post_post_id", referencedColumnName = "post_id")
    private Post post;

    public LikePost(Member member, Post post) {
        this.member = member;
        this.post = post;
    }
}
