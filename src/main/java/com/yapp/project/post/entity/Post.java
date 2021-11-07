package com.yapp.project.post.entity;

import com.yapp.project.common.entity.BaseEntity;
import com.yapp.project.member.entity.Member;
import com.yapp.project.post.entity.vo.PostStatus;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Post extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", unique = true)
    private Long id;

    @Column(name = "post_image_urls", columnDefinition = "LONGTEXT")
    private String imageUrls;

    @Column(name = "post_title", nullable = false)
    private String title;

    @Column(name = "post_category")
    private int postCategory;

    @Column(name = "post_start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "post_end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "post_region")
    private String region;

    @Column(name = "post_description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "post_view_count", nullable = false)
    @ColumnDefault("0")
    private Long viewCount;

    @Column(name = "post_status", nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'RECRUITING'")
    private PostStatus postStatus;

    @ManyToOne
    @JoinColumn(name = "post_owner_id", referencedColumnName = "member_id", nullable = false)
    private Member owner;

}
