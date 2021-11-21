package com.yapp.project.member.service;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Optional<Member> findByLoginId(String loginId){
        Optional<Member> member = memberRepository.findMemberByLoginId(loginId);
        return member;
    }

    public void create(String loginId, String email){
        Member member = Member.builder()
                .loginId(loginId)
                .email(email)
                .build();
        memberRepository.save(member);
    }
}
