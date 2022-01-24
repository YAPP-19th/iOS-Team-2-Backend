package com.yapp.project.common.Scheduler;

import com.yapp.project.apply.service.ApplyService;
import com.yapp.project.member.service.MemberService;
import com.yapp.project.post.service.PostService;
import com.yapp.project.review.service.CodeReviewHistoryService;
import com.yapp.project.review.service.TextReviewHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class EntityDeletionScheduler {
    private final ApplyService applyService;
    private final MemberService memberService;
    private final PostService postService;
    private final CodeReviewHistoryService codeReviewHistoryService;
    private final TextReviewHistoryService textReviewHistoryService;

    /**
     * cron : 초 분 시 일 월 년
     * 매일 자정(00:00:00)시에 스케줄러 실행
     */
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void deletePermanently() {
        LocalDate today = LocalDate.now();
        LocalDateTime baseDeletionTime = today.minusMonths(1).atStartOfDay();

        applyService.deleteAllExpiredDate(baseDeletionTime);

        memberService.deleteAllExpiredDate(baseDeletionTime);

        postService.deleteAllExpiredDate(baseDeletionTime);

        codeReviewHistoryService.deleteAllExpiredDate(baseDeletionTime);

        textReviewHistoryService.deleteAllExpiredDate(baseDeletionTime);

        log.info("entity deletion completed in {} / base deletion date: {}", today, baseDeletionTime);
    }
}
