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
import com.yapp.project.common.value.BasePosition;
import com.yapp.project.external.fcm.FirebaseCloudMessageService;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.repository.MemberRepository;
import com.yapp.project.post.entity.RecruitingPosition;
import com.yapp.project.post.repository.RecruitingPositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplyService {
    private final ApplyRepository applyRepository;
    private final MemberRepository memberRepository;
    private final RecruitingPositionRepository recruitingPositionRepository;
    private final ApplyConverter applyConverter;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @Transactional
    public ApplyResponse apply(long memberId, ApplyRequest request) throws IOException { // TODO: 지원 알림처리
        RecruitingPosition rp = recruitingPositionRepository.findById(request.getRecruitingPositionId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_RECRUITING_POSITION_ID));

        if (applyRepository.existsByMemberIdAndPostId(memberId, rp.getPost().getId())) { // 프로젝트 당 1회 지원 가능
            throw new IllegalRequestException(ExceptionMessage.ALREADY_APPLIED);
        }

        var recruitingPosition = recruitingPositionRepository.findById(request.getRecruitingPositionId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_RECRUITING_POSITION_ID));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.MEMBER_NOT_FOUND));

        Apply apply = applyConverter.toEntity(member, recruitingPosition, ApplyStatus.DONE_APPLYING.getCode());
        Apply applyEntity = applyRepository.save(apply);

        sendNotificationToLeader(member, applyEntity);

        return applyConverter.toApplyResponse(applyEntity);
    }

    private void sendNotificationToLeader(Member applyer, Apply apply) throws IOException {
        Member leader = apply.getPost().getOwner();

        if (!leader.isFcmTokenActive()) return;

        firebaseCloudMessageService.sendMessageTo(
                leader.getFcmToken(),
                MessageFormat.format("{0}님이 {1} 프로젝트에 지원했습니다.", applyer.getNickName(), apply.getPost().getTitle()),
                "지원내용을 확인하세요!"
        );
    }

    @Transactional
    public void approveApplication(long applyId, long leaderId) {
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_APPLY_ID));

        apply.getPost().validateLeaderOrElseThrow(leaderId);

        apply.updateApplyStatusCode(ApplyStatus.APPROVAL_FOR_PARTICIPATION.getCode());
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

        //TODO: leaderId가 post작성자인지 검증

        if (positionOpt.isPresent()) { // 직군으로 조회
            BasePosition basePosition = BasePosition.fromEnglishName(positionOpt.get());
            applies = applyRepository.findALlByBasePositionCodeAndPostId(basePosition.getCode(), postId);
        } else {
            applies = applyRepository.findAllByPostId(postId);
        }

        return applyConverter.toApplicantResponses(applies);
    }
}
