package com.yapp.project.post.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "recruiting_position")
public class RecruitingPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "recruiting_position_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recruiting_position_post_id", referencedColumnName = "post_id")
    private Post post;

    @Column(name = "recruiting_position_position_code")
    private Integer positionCode;

    @Column(name = "recruiting_position_skill_code")
    private Integer skillCode;

    @Column(name = "recruiting_position_recruiting_number")
    private Integer recruitingNumber;

    public void setPost(Post post) {
        this.post = post;
    }
}
