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
import com.yapp.project.notification.entity.value.NotificationType;
import com.yapp.project.notification.service.NotificationService;
import com.yapp.project.post.entity.Post;
import com.yapp.project.post.entity.RecruitingPosition;
import com.yapp.project.post.repository.RecruitingPositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplyService {
    private final ApplyRepository applyRepository;
    private final MemberRepository memberRepository;
    private final RecruitingPositionRepository recruitingPositionRepository;
    private final ApplyConverter applyConverter;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final NotificationService notificationService;

    @Transactional
    public ApplyResponse apply(long memberId, ApplyRequest request) {
        RecruitingPosition rp = recruitingPositionRepository.findById(request.getRecruitingPositionId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_RECRUITING_POSITION_ID));

        if (applyRepository.existsByMemberIdAndPostId(memberId, rp.getPost().getId())) { // ???????????? ??? 1??? ?????? ??????
            throw new IllegalRequestException(ExceptionMessage.ALREADY_APPLIED);
        }

        var recruitingPosition = recruitingPositionRepository.findById(request.getRecruitingPositionId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_RECRUITING_POSITION_ID));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.MEMBER_NOT_FOUND));

        Apply apply = applyConverter.toEntity(member, recruitingPosition, ApplyStatus.DONE_APPLYING.getCode());
        Apply applyEntity = applyRepository.save(apply);

        if (apply.getPost().getOwner().isFcmTokenActive()) {
            String title = MessageFormat.format("{0}?????? {1} ??????????????? ??????????????????.", member.getNickName(), apply.getPost().getTitle());
            String body = "??????????????? ???????????????!";

            sendNotificationToReceiver(apply.getPost().getOwner(), title, body, recruitingPosition.getPost());
        }

        return applyConverter.toApplyResponse(applyEntity);
    }

    @Transactional
    public void approveApplication(long applyId, long leaderId) {
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_APPLY_ID));

        apply.getPost().validateLeaderOrElseThrow(leaderId);

        apply.updateApplyStatusCode(ApplyStatus.APPROVAL_FOR_PARTICIPATION.getCode());

        if (apply.getMember().isFcmTokenActive()) {
            String title = MessageFormat.format("{0} ??????????????? ?????????????????????.", apply.getPost().getTitle());
            String body = "?????????????????? ???????????????!";

            sendNotificationToReceiver(apply.getMember(), title, body, apply.getPost());
        }
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

        //TODO: leaderId??? post??????????????? ??????

        if (positionOpt.isPresent()) { // ???????????? ??????
            BasePosition basePosition = BasePosition.fromEnglishName(positionOpt.get());
            applies = applyRepository.findALlByBasePositionCodeAndPostId(basePosition.getCode(), postId);
        } else {
            applies = applyRepository.findAllByPostId(postId);
        }

        return applyConverter.toApplicantResponses(applies);
    }

    @Transactional
    public void reject(long applyId, long leaderId) {
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_APPLY_ID));

        apply.getPost().validateLeaderOrElseThrow(leaderId);

        apply.updateApplyStatusCode(ApplyStatus.REJECT_PARTICIPATION.getCode());

        if (apply.getMember().isFcmTokenActive()) {
            String title = MessageFormat.format("???????????? {0} ??????????????? ???????????? ?????? ??? ?????????", apply.getPost().getTitle());
            String body = "?????????????????? ???????????????!";

            sendNotificationToReceiver(apply.getMember(), title, body, apply.getPost());
        }
    }

    private void sendNotificationToReceiver(Member receiver, String title, String body, Post relatedPost) {
        try {
            firebaseCloudMessageService.sendMessageTo(receiver.getFcmToken(), title, body);
        } catch (Exception e) {
            log.error(MessageFormat.format("?????? ?????? FCM ?????? ??????: leaderId: {0}", receiver.getId()));
        }

        notificationService.save(receiver.getId(), title, body, NotificationType.APPLY_FOR_PROJECT.getCode(), relatedPost.getId());
    }

    @Transactional
    public void deleteAllExpiredDate(LocalDateTime baseDeletionTime) {
        applyRepository.deleteALlExpired(baseDeletionTime);
    }
}
