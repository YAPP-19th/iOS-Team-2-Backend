package com.yapp.project.post.entity;

import com.yapp.project.common.entity.DeletableEntity;
import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.IllegalRequestException;
import com.yapp.project.member.entity.Member;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
@Table(indexes = {
        @Index(name = "POST_IX01", columnList = "post_owner_id")
})
public class Post extends DeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "post_image_urls", nullable = false)
    private String imageUrl;

    @Column(name = "post_title", nullable = false)
    private String title;

    @Column(name = "post_category", nullable = false)
    private Integer categoryCode;

    @Column(name = "post_start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "post_end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "post_region", nullable = false)
    private String region;

    @Column(name = "post_description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "post_view_count", nullable = false)
    private Long viewCount;

    @Column(name = "post_status_code", nullable = false)
    private Integer statusCode;

    @Column(name = "post_online_code", nullable = false)
    private Integer onlineCode;

    @Column(name = "post_like_count", nullable = false)
    private Long likeCount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
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
