package com.yapp.project.member.repository;

import com.yapp.project.member.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p WHERE p.member.id = :memberId")
    List<Project> getAllByMemberId(@Param("memberId") Long memberId);
}
