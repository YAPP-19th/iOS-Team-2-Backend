package com.yapp.project.apply.repository;

import com.yapp.project.apply.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
}
