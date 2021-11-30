package com.yapp.project.member.repository;

import com.yapp.project.member.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Long> {
}
