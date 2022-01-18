package com.yapp.project.review.service;

import com.yapp.project.apply.entity.Apply;
import com.yapp.project.apply.entity.value.ApplyStatus;
import com.yapp.project.apply.repository.ApplyRepository;
import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.IllegalRequestException;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.external.fcm.FirebaseCloudMessageService;
import com.yapp.project.member.entity.Member;
import com.yapp.project.member.repository.MemberRepository;
import com.yapp.project.notification.entity.value.NotificationType;
import com.yapp.project.notification.service.NotificationService;
import com.yapp.project.post.entity.Post;
import com.yapp.project.post.repository.PostRepository;
import com.yapp.project.review.dto.request.TextReviewCreateRequest;
import com.yapp.project.review.dto.response.TextReviewSimpleResponse;
import com.yapp.project.review.entity.CodeReviewHistory;
import com.yapp.project.review.entity.TextReviewHistory;
import com.yapp.project.review.repository.TextReviewHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextReviewHistoryService {
    private final TextReviewHistoryRepository textReviewHistoryRepository;
    private final TextReviewHistoryConverter converter;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ApplyRepository applyRepository;

    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final NotificationService notificationService;

    @Transactional
    public void create(long reviewerId, long revieweeId, long postId, TextReviewCreateRequest request) {
        Member reviewer = memberRepository.findById(reviewerId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));
        Member reviewee = memberRepository.findById(revieweeId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        if (reviewer.getId().longValue() == reviewee.getId().longValue()) {
            throw new IllegalRequestException(ExceptionMessage.NO_SELF_REVIEW);
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_ID));

        if (textReviewHistoryRepository.existsByReviewerAndTargetMemberAndPost(reviewer, reviewee, post)) {
            throw new IllegalRequestException(ExceptionMessage.ALREADY_REVIEWED);
        }

        if (post.getOwner().getId().longValue() == reviewee.getId().longValue()) { // 타겟이 프로젝트 리더인 경우
            Apply reviewerApply = applyRepository.findByMemberAndPost(reviewer, post)
                    .orElseThrow(() -> new NotFoundException(ExceptionMessage.ILLEGAL_TARGETMEMBER));

            ApplyStatus.validateApprovedCodeOrElseThrow(reviewerApply.getApplyStatusCode());
        } else {  // 타겟이 팀원인 경우
            if (reviewer.getId().longValue() == post.getOwner().getId().longValue()) { // 리뷰어가 프로젝트 리더인 경우
                Apply revieweeApply = applyRepository.findByMemberAndPost(reviewee, post)
                        .orElseThrow(() -> new NotFoundException(ExceptionMessage.ILLEGAL_TARGETMEMBER));

                ApplyStatus.validateApprovedCodeOrElseThrow(revieweeApply.getApplyStatusCode());
            } else { // 참여자가 또 다른 참여자에게 리뷰를 남기는 경우
                Apply reviewerApply = applyRepository.findByMemberAndPost(reviewer, post)
                        .orElseThrow(() -> new NotFoundException(ExceptionMessage.ILLEGAL_TARGETMEMBER));
                Apply revieweeApply = applyRepository.findByMemberAndPost(reviewer, post)
                        .orElseThrow(() -> new NotFoundException(ExceptionMessage.ILLEGAL_TARGETMEMBER));

                ApplyStatus.validateApprovedCodeOrElseThrow(reviewerApply.getApplyStatusCode());
                ApplyStatus.validateApprovedCodeOrElseThrow(revieweeApply.getApplyStatusCode());
            }
        }

        var textReviewHistory = converter.toEntity(reviewer, reviewee, post, request.getTitle(), request.getContent());
        textReviewHistoryRepository.save(textReviewHistory);

        sendNotificationToReviewee(reviewee, post);
    }

    @Transactional(readOnly = true)
    public Page<TextReviewSimpleResponse> findAllByPages(Long targetMemberId, Pageable pageable) {
        Member targetMember = memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_MEMBER_ID));

        Page<TextReviewHistory> allByMember = textReviewHistoryRepository.findAllByTargetMember(targetMember, pageable);

        return allByMember.map(t -> converter.toTextReviewSimpleResponse(t));
    }

    @Transactional(readOnly = true)
    public List<TextReviewSimpleResponse> findAllByMemberAndPost(long currentMemberId, long postId) {
        List<TextReviewHistory> textReviews = textReviewHistoryRepository.findAllByTargetMemberIdAndPostId(currentMemberId, postId);

        return converter.toTextReviewSimpleResponses(textReviews);
    }

    private void sendNotificationToReviewee(Member reviewee, Post post) {
        if (!reviewee.isFcmTokenActive()) return;

        String title = MessageFormat.format("{0} 프로젝트 팀원으로부터 리뷰가 추가되었어요", post.getTitle());
        String body = "리뷰내용을 확인하세요!";

        try {
            firebaseCloudMessageService.sendMessageTo(reviewee.getFcmToken(), title, body);
        } catch (Exception e) {
            log.error(MessageFormat.format("리뷰 등록 알림 FCM 전송 실패: revieweeId: {0}", reviewee.getId()));
        }

        notificationService.save(reviewee.getId(), title, body, NotificationType.REVIEW_REGISTRATION.getCode());
    }
}
