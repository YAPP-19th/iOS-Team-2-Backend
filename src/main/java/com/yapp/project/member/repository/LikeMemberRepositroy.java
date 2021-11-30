package com.yapp.project.member.repository;

import com.yapp.project.member.entity.LikeMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeMemberRepositroy extends JpaRepository<LikeMember, Long> {
}
