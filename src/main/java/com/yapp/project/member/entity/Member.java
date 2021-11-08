package com.yapp.project.member.entity;

import com.yapp.project.common.entity.BaseEntity;
import com.yapp.project.post.entity.JoinedInfo;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_nick_name", length = 10)
    private String nickName;

    // TODO: post 도메인과 관련된 부분만 임시 구현한 상태
}
