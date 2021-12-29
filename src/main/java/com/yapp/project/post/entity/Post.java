package com.yapp.project.post.entity;

import com.yapp.project.common.entity.DeletableEntity;
import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.IllegalRequestException;
import com.yapp.project.member.entity.Member;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE post SET is_deleted = true WHERE post_id=?")
@Where(clause = "is_deleted = false")
public class Post extends DeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "post_image_urls", columnDefinition = "LONGTEXT")
    private String imageUrl;

    @Column(name = "post_title")
    private String title;

    @Column(name = "post_category")
    private Integer categoryCode;

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

    @Column(name = "post_status_code")
    private Integer statusCode;

    @Column(name = "post_online_code")
    private Integer onlineCode;

    @Column(name = "post_like_count")
    private Long likeCount;

    @ManyToOne
    @JoinColumn(name = "post_owner_id", referencedColumnName = "member_id")
    private Member owner;

    public void addViewCount() {
        this.viewCount++;
    }

    public void addLikeCount() {
        this.likeCount++;
    }

    public void substractLikeCount() {
        if (this.likeCount <= 0) {
            throw new IllegalRequestException(ExceptionMessage.INVALID_LIKE_COUNT);
        }

        this.likeCount--;
    }

    public void updateInfos(
            String imageUrl,
            String title,
            int categoryCode,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String region,
            String description,
            int onlineCode
    ) {

        this.imageUrl = imageUrl;
        this.title = title;
        this.categoryCode = categoryCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.region = region;
        this.description = description;
        this.onlineCode = onlineCode;
    }

    public void updateStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void validateLeaderOrElseThrow(long memberId) {
        if (this.owner.getId().longValue() != memberId) {
            throw new IllegalRequestException(ExceptionMessage.INVALID_MEMBER);
        }
    }
}
