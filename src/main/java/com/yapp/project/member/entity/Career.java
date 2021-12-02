package com.yapp.project.member.entity;

import com.yapp.project.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Career extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "career_id")
    private Long id;

    @Column(name = "career_company_name")
    private String companyName;

    @Column(name = "career_start_date")
    private LocalDate startDate;

    @Column(name = "career_end_date")
    private LocalDate endDate;

    @Column(name = "career_now_works")
    private Boolean nowWorks;

    @Column(name = "career_team_name")
    private String teamName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "career")
    private List<Work> works  = new ArrayList<>();; // 주요 성과
}