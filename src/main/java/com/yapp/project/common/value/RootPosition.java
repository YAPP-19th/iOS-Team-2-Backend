package com.yapp.project.common.value;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RootPosition {
    DEVELOPER(1, "개발"),
    PLANNER(2, "기획"),
    DESIGNER(3, "디자인"),

    ;

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

    public static RootPosition fromEnglishName(String rootPositionEngName) {
        return Arrays.stream(RootPosition.values())
                .filter(v -> v.name().equals(rootPositionEngName.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_ROOT_POSITION_NAME));
    }
}
