package com.yapp.project.member.entity;

import com.yapp.project.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity<Long> {  //TODO: 1차 구현 상태. 세분화 할 것.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "member_position_code")
    private Integer positionCode;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_token")
    private String token;

    @Column(name = "member_ogin_id")
    private String loginId;

    @Column(name = "member_level")
    private Integer level;

    @Column(name = "member_skill_code")
    private Integer skillCode;
}
