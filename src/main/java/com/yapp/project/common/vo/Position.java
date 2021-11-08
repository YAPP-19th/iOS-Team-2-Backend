package com.yapp.project.common.vo;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Position {
    // default position //
    DEVELOPER_DEFAULT(1),
    PLANNER_DEFAULT(2),
    DESIGNER_DEFAULT(3),

    // developer segmentation //
    DEVELOPER_HYBRID(4),
    DEVELOPER_IOS(5),
    DEVELOPER_AOS(6),
    DEVELOPER_WEBFRONT(7),
    DEVELOPER_WEBBACK(8),
    DEVELOPER_BLOCKCHAIN(9),
    DEVELOPER_AI(10),

    // planner segmentation //
    PLANNER_BUSINESS(11),
    PLANNER_UIUX(12),
    PLANNER_PROJECTMANAGER(13),
    PLANNER_PRODUCTPLANNER(14),
    PLANNER_SERVICEOPERATOR(15),

    // designer segmentation //
    DESIGNER_UIUX(16),
    DESIGNER_WEB(17),
    DESIGNER_MOBILE(18),
    DESIGNER_ILLUSTRATOR(19),
    DESIGNER_UX(20);

    private final int positionCode;

    Position(int positionCode) {
        this.positionCode = positionCode;
    }

    public static Position of(int positionCode) {
        return Arrays.stream(Position.values())
                .filter(v -> v.positionCode == positionCode)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POSITION_CODE));
    }
}
