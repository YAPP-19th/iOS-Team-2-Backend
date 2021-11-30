package com.yapp.project.apply.entity.value;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import com.yapp.project.common.value.Position;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ApplyStatus {
    DONE_APPLYING("지원완료", 2),
    APPROVAL_FOR_PARTICIPATION("참여승인", 4),

    ;

    private final String applyStatusName;
    private final int applyStatusCode;

    ApplyStatus(String applyStatusName, int applyStatusCode) {
        this.applyStatusName = applyStatusName;
        this.applyStatusCode = applyStatusCode;
    }

    public static ApplyStatus of(int applyStatusCode) {
        return Arrays.stream(ApplyStatus.values())
                .filter(v -> v.applyStatusCode == applyStatusCode)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_APPLY_STATUS_CODE));
    }

    public static ApplyStatus of(String applyStatusName) {
        return Arrays.stream(ApplyStatus.values())
                .filter(v -> v.applyStatusName.equals(applyStatusName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_APPLY_STATUS_NAME));
    }
}
