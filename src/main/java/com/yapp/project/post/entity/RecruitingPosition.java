package com.yapp.project.post.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "recruiting_position")
public class RecruitingPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruiting_position_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "recruiting_position_post_id", referencedColumnName = "post_id")
    private Post post;

    @Column(name = "recruiting_position_base_position_code", nullable = false)
    private Integer basePositionCode;

    @Column(name = "recruiting_position_position_code", nullable = false)
    private Integer positionCode;

    @Column(name = "recruiting_position_recruiting_number", nullable = false)
    private Integer recruitingNumber;

    public void setPost(Post post) {
        this.post = post;
    }
}
