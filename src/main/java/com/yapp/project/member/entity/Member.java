package com.yapp.project.member.entity;

import com.yapp.project.common.entity.DeletableEntity;
import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.IllegalRequestException;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE member SET is_deleted = true WHERE member_id=?")
@Where(clause = "is_deleted = false")
public class Member extends DeletableEntity {  //TODO: 1차 구현 상태. 세분화 할 것.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_nick_name", length = 10, nullable = false)
    private String nickName;

    @Column(name = "member_profile_image_url", columnDefinition = "TEXT")
    private String profileImageUrl;

    @Column(name = "member_address", nullable = false)
    private String address;

    @Column(name = "member_introduce", nullable = false)
    private String introduce;

    @Column(name = "member_base_position_code", nullable = false)
    private Integer basePositionCode;

    @Column(name = "member_position_code", nullable = false)
    private String positionCode;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_token", columnDefinition = "TEXT")
    private String token;

    @Column(name = "member_login_id")
    private String loginId;

    @Column(name = "member_score", nullable = false)
    private Integer score;

    @Column(name = "member_like_count", nullable = false)
    private Long likeCount;

    @Column(name = "member_fcm_token", columnDefinition = "TEXT")
    private String fcmToken;

    @Column(name = "member_is_fcmToken_active", nullable = false, columnDefinition = "boolean default true")
    private boolean isFcmTokenActive;

    @Column(name = "member_portfolio_link", columnDefinition = "TEXT", nullable = false)
    private String portfolioLink;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Project> projects = new ArrayList<>();

    public boolean isSameMember(Member member) {
        return this.id.longValue() == member.getId().longValue() ? true : false;
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

    public synchronized void updateFcmTokenAndActiveStatus(String fcmToken, Boolean isFcmTokenActive) {
        this.fcmToken = fcmToken;
        this.isFcmTokenActive = isFcmTokenActive == null ? true : isFcmTokenActive;
    }

    public void updateInfo(String address, String introduce, int basePositionCode, String nickName, String positionCode, int score, String portfolioLink) {
        this.address = address;
        this.introduce = introduce;
        this.basePositionCode = basePositionCode;
        this.nickName = nickName;
        this.positionCode = positionCode;
        this.score = score;
        this.portfolioLink = portfolioLink;
    }
}
