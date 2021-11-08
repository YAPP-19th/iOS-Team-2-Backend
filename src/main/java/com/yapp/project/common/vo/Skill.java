package com.yapp.project.common.vo;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Skill {
    // default skill //
    DEVELOPER_DEFAULT(1),
    PLANNER_DEFAULT(2),
    DESIGNER_DEFAULT(3),

    // developer segmentation //
    DEVELOPER_OBJECTC(4),
    DEVELOPER_SWIFT(5),
    DEVELOPER_PYTHON(6),
    DEVELOPER_RUBY(7),
    DEVELOPER_JAVA(8),
    DEVELOPER_KOTLIN(9),
    DEVELOPER_C(10),
    DEVELOPER_LUA(11),
    DEVELOPER_HTML(12),
    DEVELOPER_CSS(13),
    DEVELOPER_JAVASCRIPT(14),
    DEVELOPER_PHP(15),
    DEVELOPER_GO(16),
    DEVELOPER_NODE(17),
    DEVELOPER_SOLIDITY(18),
    DEVELOPER_SOMPLICITY(19),
    DEVELOPER_LISP(20),
    DEVELOPER_R(21),
    DEVELOPER_PROLOG(22),
    DEVELOPER_JULIA(23),

    // planner segmentation //
    PLANNER_STORYBOARD(24),
    PLANNER_DOCUMENTATION(25),
    PLANNER_DATAANALYSIS(26),
    PLANNER_GA(27),
    PLANNER_MOBILEPLANNING(28),
    PLANNER_WEB_PLANNING(29),
    PLANNER_PUBLISHING(30),

    // designer segmentation //
    DESIGNER_ADOBEXD(31),
    DESIGNER_SKETCH(32),
    DESIGNER_ZEPLIN(33),
    DESIGNER_PROTOIO(34),
    DESIGER_FIGMA(35),
    DESIGNER_PHOTOSHOP(36),
    DESIGNER_ILLUSTRATOR(37);

    private final int skillCode;

    Skill(int skillCode) {
        this.skillCode = skillCode;
    }

    public static Skill of(int skillCode) {
        return Arrays.stream(Skill.values())
                .filter(v -> v.skillCode == skillCode)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_SKILL_CODE));
    }
}
