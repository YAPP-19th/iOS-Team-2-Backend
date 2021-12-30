package com.yapp.project.apply.service;

import com.yapp.project.apply.dto.request.ApplyRequest;
import com.yapp.project.apply.dto.response.ApplicantResponse;
import com.yapp.project.apply.dto.response.ApplyResponse;
import com.yapp.project.apply.entity.Apply;
import com.yapp.project.apply.entity.value.ApplyStatus;
import com.yapp.project.apply.repository.ApplyRepository;
import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.IllegalRequestException;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.common.value.Position;
import com.yapp.project.common.value.RootPosition;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.repository.MemberRepository;
import com.yapp.project.post.repository.RecruitingPositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplyService {
    private final ApplyRepository applyRepository;
    private final MemberRepository memberRepository;
    private final RecruitingPositionRepository recruitingPositionRepository;
    private final ApplyConverter applyConverter;

    @Transactional
    public ApplyResponse apply(long memberId, ApplyRequest request) { // TODO: 지원 알림처리
        if (applyRepository.existsByMemberIdAndRecruitingPositionId(memberId, request.getRecruitingPositionId())) {
            throw new IllegalRequestException(ExceptionMessage.ALREADY_APPLIED);
        }

        var recruitingPosition = recruitingPositionRepository.findById(request.getRecruitingPositionId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_RECRUITING_POSITION_ID));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.MEMBER_NOT_FOUND));

        Apply apply = applyConverter.toEntity(member, recruitingPosition, ApplyStatus.DONE_APPLYING.getApplyStatusCode());
        Apply applyEntity = applyRepository.save(apply);

        return applyConverter.toApplyResponse(applyEntity);
    }

    @Transactional
    public void approveApplication(long applyId, long leaderId) {
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_APPLY_ID));

        apply.getPost().validateLeaderOrElseThrow(leaderId);

        apply.updateApplyStatusCode(ApplyStatus.APPROVAL_FOR_PARTICIPATION.getApplyStatusCode());
    }

    @Transactional
    public void cancelApplication(long applyId, long applicantId) {
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_APPLY_ID));

        apply.validateApplicantOrElseThrow(applicantId);

        applyRepository.deleteById(applyId);
    }

    @Transactional(readOnly = true)
    public List<ApplicantResponse> getApplyList(long postId, Optional<String> positionOpt, long leaderId) {
        List<Apply> applies;

        if (positionOpt.isPresent()) { // 직군으로 조회
            RootPosition rootPosition = RootPosition.fromEnglishName(positionOpt.get());
            applies = applyRepository.findALlByRootPositionCodeAndPostId(rootPosition.getRootPositionCode(), postId);
        } else {
            applies = applyRepository.findAllByPostId(postId);
        }

        return applyConverter.toApplicantResponses(applies);
    }
}
