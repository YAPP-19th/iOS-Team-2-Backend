package com.yapp.project.member.entity;

import com.yapp.project.common.entity.DeletableEntity;
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
@SQLDelete(sql = "UPDATE member SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
public class Member extends DeletableEntity {  //TODO: 1차 구현 상태. 세분화 할 것.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_nick_name", length = 10)
    private String nickName;

    @Column(name = "member_profile_image_url", columnDefinition = "TEXT")
    private String profileImageUrl;

    @Column(name = "member_address")
    private String address;

    @Column(name = "member_introduce")
    private String introduce;

    @Column(name = "member_base_position_code")
    private Integer basePositionCode;

    @Column(name = "member_position_code")
    private String positionCode;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_token")
    private String token;

    @Column(name = "member_login_id")
    private String loginId;

    @Column(name = "member_score")
    private Integer score;

    @Column(name = "member_like_count")
    private Long likeCount;

    @Column(name = "member_portfolio_link", columnDefinition = "TEXT")
    private String portfolioLink;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Career> careers = new ArrayList<>();

    public boolean isSameMember(Member member){
        return this.id.longValue() == member.getId().longValue() ? true : false;
    }
}
