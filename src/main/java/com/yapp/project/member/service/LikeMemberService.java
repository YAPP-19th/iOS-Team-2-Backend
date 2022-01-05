package com.yapp.project.member.service;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.member.dto.response.LikeMemberResponse;
import com.yapp.project.member.entity.LikeMember;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.repository.LikeMemberRepositroy;
import com.yapp.project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeMemberService {
    private final LikeMemberRepositroy likeMemberRepositroy;
    private final MemberRepository memberRepository;
    private final LikeMemberConverter likeMemberConverter;

    @Transactional(readOnly = true)
    public Page<LikeMemberResponse> findAll(Pageable pageable, long fromMemberId) {
        Page<LikeMember> likeMemberPage = likeMemberRepositroy.findAllByFromMemberId(pageable, fromMemberId);

        return likeMemberPage.map(lm -> likeMemberConverter.toLikeMemberResponse(lm));
    }

    @Transactional
    public boolean switchLikeMemberStatus(Long fromMemberId, Long targetMemberId) {
        Member fromMember = memberRepository.findById(fromMemberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        Member toMember = memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        if (likeMemberRepositroy.existsByFromMemberAndToMember(fromMember, toMember)) {
            LikeMember likeMember = likeMemberRepositroy.findByFromMemberAndToMember(fromMember, toMember)
                    .orElseThrow(() -> new NotFoundException(ExceptionMessage.INVALID_HTTP_REQUEST));

            likeMemberRepositroy.delete(likeMember);

            toMember.substractLikeCount();
            
            return false;
        } else {
            LikeMember likeMember = new LikeMember(fromMember, toMember);
            likeMemberRepositroy.save(likeMember);

            toMember.addLikeCount();

            return true;
        }
    }
}
