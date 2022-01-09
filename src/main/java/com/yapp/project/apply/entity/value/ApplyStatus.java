package com.yapp.project.apply.entity.value;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.IllegalRequestException;
import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ApplyStatus {
    DONE_APPLYING("지원완료", 2),
    APPROVAL_FOR_PARTICIPATION("참여승인", 4),
    REJECT_PARTICIPATION("참여거절", 8),

    ;

    private final String name;
    private final int code;

    ApplyStatus(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static ApplyStatus of(int applyStatusCode) {
        return Arrays.stream(ApplyStatus.values())
                .filter(v -> v.code == applyStatusCode)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_APPLY_STATUS_CODE));
    }

    public static ApplyStatus of(String applyStatusName) {
        return Arrays.stream(ApplyStatus.values())
                .filter(v -> v.name.equals(applyStatusName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_APPLY_STATUS_NAME));
    }

    public static void validateApprovedCodeOrElseThrow(int code) {
        if(code != APPROVAL_FOR_PARTICIPATION.code){
            throw new IllegalRequestException(ExceptionMessage.NOT_APPROVED_APPLY);
        }
    }

    public static boolean isApproved(int code) {
        return ApplyStatus.APPROVAL_FOR_PARTICIPATION.getCode() == code;
    }
}
