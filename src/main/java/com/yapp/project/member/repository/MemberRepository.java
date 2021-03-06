package com.yapp.project.member.repository;

import com.yapp.project.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Override
    Optional<Member> findById(Long id);

    Optional<Member> findMemberByLoginId(String loginId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.token = :refreshToken WHERE m.loginId = :loginId")
    void setRefreshTokenByLoginId(@Param("loginId") String loginId, @Param("refreshToken") String refreshToken);

    @Query("SELECT m.token FROM Member m WHERE m.loginId = :loginId")
    String getTokenByLoginId(@Param("loginId") String loginId);

    boolean existsByNickName(String nickName);

    Page<Member> findAllByBasePositionCode(Pageable pageable, int basePositionCode);

    Page<Member> findAllByPositionCodeContains(String position, Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM Member m WHERE m.isDeleted = true and m.lastModifiedDate < :baseDeletionTime")
    void deleteAllExpired(LocalDateTime baseDeletionTime);
}
