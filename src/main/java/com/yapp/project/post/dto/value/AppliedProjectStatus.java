package com.yapp.project.post.dto.value;

import com.yapp.project.apply.entity.value.ApplyStatus;
import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;

import java.time.LocalDateTime;

public interface AppliedProjectStatus {
    String APPLIED = "지원한";
    String REJECTED = "거절됨";
    String PARTICIPATING = "참여중";
    String COMPLETION = "완료";

    static String makeAppliedProjectStatus(int applyStatusCode, LocalDateTime projectEndDate) {
        if (ApplyStatus.REJECT_PARTICIPATION.getCode() == applyStatusCode) {
            return REJECTED;
        }

        if (ApplyStatus.DONE_APPLYING.getCode() == applyStatusCode) {
            return APPLIED;
        }

        if (ApplyStatus.APPROVAL_FOR_PARTICIPATION.getCode() == applyStatusCode) {
            var now = LocalDateTime.now();

            if (projectEndDate.isBefore(now) || projectEndDate.isEqual(now)) {
                return COMPLETION;
            }

            return PARTICIPATING;
        }

        throw new NotFoundException(ExceptionMessage.NOT_EXIST_APPLY_STATUS_CODE);
    }
}
