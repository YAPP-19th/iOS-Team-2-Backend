package com.yapp.project.post.entity;

import com.yapp.project.common.entity.BaseEntity;
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
public class RecruitingPosition extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruiting_position_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "recruiting_position_post_id", referencedColumnName = "post_id")
    private Post post;

    @Column(name = "recruiting_position_root_position_code")
    private Integer rootPositionCode;

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
