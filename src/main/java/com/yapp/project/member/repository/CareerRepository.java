package com.yapp.project.member.repository;

import com.yapp.project.member.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, Long> {
}
