package com.yapp.project.post.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "recruiting_position_detail")
public class RecruitingPositionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "recruiting_position_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiting_position_detail_post_id", referencedColumnName = "post_id")
    private Post post;

    @Column(name = "recruiting_position_detail_position_code")
    private int positionCode;

    @Column(name = "recruiting_position_detail_skill_code")
    private int skillCode;

    @Column(name = "recruiting_position_detail_recruiting_number")
    private int recruitingNumber;

    public void setPost(Post post) {
        this.post = post;
    }
}
