package com.yapp.project.member.repository;

import com.yapp.project.member.entity.LikeMember;
import com.yapp.project.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeMemberRepositroy extends JpaRepository<LikeMember, Long> {
    @Override
    <S extends LikeMember> S save(S entity);

    @Override
    void delete(LikeMember entity);

    boolean existsByFromMemberAndToMember(Member fromMember, Member toMember);
}
