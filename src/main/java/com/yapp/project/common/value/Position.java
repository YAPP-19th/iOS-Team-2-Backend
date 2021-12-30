package com.yapp.project.common.value;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum Position {
    // default position //
    DEVELOPER_DEFAULT(1, "개발 기타"),
    PLANNER_DEFAULT(2, "기획 기타"),
    DESIGNER_DEFAULT(3, "디자인 기타"),

    // developer segmentation //
    DEVELOPER_HYBRID(4, "하이브리드 개발"),
    DEVELOPER_IOS(5, "iOS 개발"),
    DEVELOPER_AOS(6, "AOS 개발"),
    DEVELOPER_WEBFRONT(7, "웹 프론트 개발"),
    DEVELOPER_WEBBACK(8, "웹 백엔드 개발"),
    DEVELOPER_BLOCKCHAIN(9, "블록체인 개발"),
    DEVELOPER_AI(10, "AI 개발"),

    // planner segmentation //
    PLANNER_BUSINESS(11, "비즈니스 기획"),
    PLANNER_UIUX(12, "UI/UX 기획"),
    PLANNER_PROJECTMANAGER(13, "PM"),
    PLANNER_PRODUCTPLANNER(14, "상품 기획"),
    PLANNER_SERVICEOPERATOR(15, "서비스 기획"),

    // designer segmentation //
    DESIGNER_UIUX(16, "UI/UX 디자인"),
    DESIGNER_WEB(17, "웹 디자인"),
    DESIGNER_MOBILE(18, "모바일 디자인"),
    DESIGNER_ILLUSTRATOR(19, "일러스트레이터"),
    DESIGNER_UX(20, "UX 디자인"),

    ;

    private final int code;
    private final String name;

    Position(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Position of(int positionCode) {
        return Arrays.stream(Position.values())
                .filter(v -> v.code == positionCode)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POSITION_CODE));
    }

    public static Position of(String positionName) {
        return Arrays.stream(Position.values())
                .filter(v -> v.name.equals(positionName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POSITION_NAME));
    }

    public static BasePosition getBasePosition(int positionCode){
        String prefix = Position.of(positionCode).name().split("_")[0].toUpperCase();

        if(BasePosition.DESIGNER.toString().toUpperCase().equals(prefix))
            return BasePosition.DESIGNER;
        else if(BasePosition.DEVELOPER.toString().toUpperCase().equals(prefix))
            return BasePosition.DEVELOPER;
        else if (BasePosition.PLANNER.toString().toUpperCase().equals(prefix))
            return BasePosition.PLANNER;

        throw new NotFoundException(ExceptionMessage.NOT_EXIST_BASE_POSITION_NAME);
    }

    public static List<String> listOf(String basePosition) {
        List<String> list = Stream.of(Position.values())
                .filter(name->name.toString().contains(basePosition.toUpperCase()))
                .map(name->name.getName())
                .collect(Collectors.toList());
        return list;
    }
}
