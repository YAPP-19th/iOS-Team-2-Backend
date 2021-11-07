package com.yapp.project.post.entity;

import com.yapp.project.common.entity.BaseEntity;
import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.vo.PostStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "post_image_urls", columnDefinition = "LONGTEXT")
    private String imageUrls;

    @Column(name = "post_title")
    private String title;

    @Column(name = "post_category")
    private int postCategory;

    @Column(name = "post_start_date")
    private LocalDateTime startDate;

    @Column(name = "post_end_date")
    private LocalDateTime endDate;

    @Column(name = "post_region")
    private String region;

    @Column(name = "post_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "post_view_count")
    private Long viewCount;

    @Column(name = "post_status")
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    @ManyToOne
    @JoinColumn(name = "post_owner_id", referencedColumnName = "member_id")
    private Member owner;

}
