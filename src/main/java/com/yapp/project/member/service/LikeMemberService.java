package com.yapp.project.member.service;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.IllegalRequestException;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.common.value.Position;
import com.yapp.project.member.dto.LikeMemberResponse;
import com.yapp.project.member.entity.LikeMember;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.repository.LikeMemberRepositroy;
import com.yapp.project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeMemberService {
    private final LikeMemberRepositroy likeMemberRepositroy;
    private final MemberRepository memberRepository;

    @Transactional
    public void like(Long fromMemberId, Long toMemberId){
        Member fromMember = memberRepository.findById(fromMemberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        Member toMember = memberRepository.findById(toMemberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        if(likeMemberRepositroy.existsByFromMemberAndToMember(fromMember, toMember))
            throw new IllegalRequestException(ExceptionMessage.ALREADY_LIKE_MEMBER);

        LikeMember likeMember = new LikeMember(fromMember, toMember);
        likeMemberRepositroy.save(likeMember);
    }

    @Transactional
    public void cancelLike(Long fromMemberId, Long toMemberId){
        Member fromMember = memberRepository.findById(fromMemberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        Member toMember = memberRepository.findById(toMemberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        if(!likeMemberRepositroy.existsByFromMemberAndToMember(fromMember, toMember))
            throw new IllegalRequestException(ExceptionMessage.LIKE_MEMBER_YET);

        LikeMember likeMember= likeMemberRepositroy.findByFromMemberAndToMember(fromMember, toMember);
        likeMemberRepositroy.delete(likeMember);
    }

    public LikeMemberResponse findAll(Long fromMemberId) {
        Member fromMember = memberRepository.findById(fromMemberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        List<LikeMember> likeMembers = likeMemberRepositroy.findAllByFromMember(fromMember);

        LikeMemberResponse response = new LikeMemberResponse();
        for(var likeMember : likeMembers){
            var toMember = likeMember.getToMember();
            var memberInfo = new LikeMemberResponse.LikedMember(
                    toMember.getId(),
                    toMember.getNickName(),
                    toMember.getProfileImageUrl(),
                    toMember.getAddress(),
                    Position.of(toMember.getPositionCode()).getPositionName()
            );

            response.getLikedMembers().add(memberInfo);
        }

        return response;
    }
}