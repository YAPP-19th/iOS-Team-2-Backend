package com.yapp.project.common.value;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RootPosition {
    DEVELOPER_DEFAULT(1, "개발"),
    PLANNER_DEFAULT(2, "디자인"),
    DESIGNER_DEFAULT(3, "기획");

    private final int rootPositionCode;
    private final String rootPositionName;

    RootPosition(int rootPositionCode, String rootPositionName) {
        this.rootPositionCode = rootPositionCode;
        this.rootPositionName = rootPositionName;
    }

    public static RootPosition of(int rootPositionCode) {
        return Arrays.stream(RootPosition.values())
                .filter(v -> v.rootPositionCode == rootPositionCode)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_ROOT_POSITION_CODE));
    }

    public static RootPosition of(String rootPositionName) {
        return Arrays.stream(RootPosition.values())
                .filter(v -> v.rootPositionName.equals(rootPositionName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_ROOT_POSITION_NAME));
    }
}
