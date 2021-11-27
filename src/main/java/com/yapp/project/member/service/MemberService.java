package com.yapp.project.member.service;
import com.yapp.project.member.dto.CheckNameResponse;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.repository.MemberRepository;
import com.yapp.project.post.service.PostConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberConverter memberConverter;
    private final MemberRepository memberRepository;

    public String findByLoginId(String loginId){
        Optional<Member> member = memberRepository.findMemberByLoginId(loginId);
        String memberId = "";
        if(!member.isEmpty()){
            memberId = member.get().getLoginId();
        }
        return memberId;
    }
    @Transactional
    public Long create(String loginId){
        Member member = Member.builder()
                .loginId(loginId)
                .build();
        Member m = memberRepository.save(member);
        return m.getId();
    }

    public CheckNameResponse checkDuplicateName(String name){
        return memberConverter.toCheckNameResponse(memberRepository.existsByNickName(name));
    }
}
