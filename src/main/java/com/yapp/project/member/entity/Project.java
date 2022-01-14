package com.yapp.project.member.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "PROJECT_IX01", columnList = "project_member_id")
})
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(name = "project_name", nullable = false)
    private String name;

    @Column(name = "project_start_date")
    private LocalDate startDate;

    @Column(name = "project_end_date")
    private LocalDate endDate;

    @Column(name = "project_description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "project_member_id", referencedColumnName = "member_id")
    private Member member;
}
