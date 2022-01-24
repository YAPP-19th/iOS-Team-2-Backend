package com.yapp.project.apply.scheduler;

import com.yapp.project.apply.service.ApplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApplyScheduler {
    private final ApplyService applyService;

    /**
     * cron : 초 분 시 일 월 년
     * 매일 자정(00:00:00)시에 스케줄러 실행
     */
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void deletePermanently() {
        LocalDate today = LocalDate.now();
        LocalDateTime baseDeletionTime = today.minusMonths(1).atStartOfDay();

        applyService.deleteAllExpiredDate(baseDeletionTime);
        log.info("apply deletion completed in {} / base deletion date: {}", today, baseDeletionTime);
    }
}
