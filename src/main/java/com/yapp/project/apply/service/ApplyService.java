package com.yapp.project.apply.service;

import com.yapp.project.apply.dto.request.ApplyRequest;
import com.yapp.project.apply.entity.Apply;
import com.yapp.project.apply.entity.value.ApplyStatus;
import com.yapp.project.apply.repository.ApplyRepository;
import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.IllegalRequestException;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.repository.MemberRepository;
import com.yapp.project.post.repository.RecruitingPositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplyService {
    private final ApplyRepository applyRepository;
    private final MemberRepository memberRepository;
    private final RecruitingPositionRepository recruitingPositionRepository;
    private final ApplyConverter applyConverter;

    @Transactional
    public void apply(Long memberId, ApplyRequest request) { // TODO: 지원 알림처리
        var recruitingPosition = recruitingPositionRepository.findById(request.getRecruitingPositionId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_RECRUITING_POSITION_ID));

        long count = applyRepository.countByRecruitingPosition(recruitingPosition);

        if (count >= recruitingPosition.getRecruitingNumber())
            throw new IllegalRequestException(ExceptionMessage.EXCEEDED_APPLICANTS_NUMBER);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.MEMBER_NOT_FOUND));

        applyConverter.toEntity(member, recruitingPosition, ApplyStatus.DONE_APPLYING.getApplyStatusCode());
    }

    @Transactional
    public void approveApplication(Long applyId) {
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_APPLY_ID));

        apply.updateApplyStatusCode(ApplyStatus.APPROVAL_FOR_PARTICIPATION.getApplyStatusCode());
    }

    @Transactional
    public void cancelApplication(Long applyId, Long applicantId) {
        if (applyRepository.existsById(applyId))
            throw new NotFoundException(ExceptionMessage.NOT_EXIST_APPLY_ID);

        applyRepository.deleteById(applyId);
    }

}
