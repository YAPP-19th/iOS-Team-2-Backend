package com.yapp.project.common.value;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BasePosition {
    DEVELOPER(1, "개발"),
    PLANNER(2, "기획"),
    DESIGNER(3, "디자인"),

    ;

    private final int code;
    private final String name;

    BasePosition(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static BasePosition of(int basePositionCode) {
        return Arrays.stream(BasePosition.values())
                .filter(v -> v.code == basePositionCode)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_BASE_POSITION_CODE));
    }

    public static BasePosition of(String basePositionName) {
        return Arrays.stream(BasePosition.values())
                .filter(v -> v.name.equals(basePositionName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_BASE_POSITION_NAME));
    }

    public static BasePosition fromEnglishName(String basePositionEngName) {
        return Arrays.stream(BasePosition.values())
                .filter(v -> v.name().equals(basePositionEngName.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_BASE_POSITION_NAME));
    }
}
