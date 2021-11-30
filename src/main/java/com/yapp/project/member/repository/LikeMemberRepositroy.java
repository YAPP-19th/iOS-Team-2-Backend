package com.yapp.project.member.repository;

import com.yapp.project.member.entity.LikeMember;
import com.yapp.project.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeMemberRepositroy extends JpaRepository<LikeMember, Long> {
    @Override
    <S extends LikeMember> S save(S entity);

    @Override
    void delete(LikeMember entity);

    boolean existsByFromMemberAndToMember(Member fromMember, Member toMember);

    List<LikeMember> findAllByFromMember(Member fromMember);
}
