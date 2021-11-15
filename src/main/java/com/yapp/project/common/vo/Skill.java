package com.yapp.project.common.vo;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Skill {
    // default skill //
    DEVELOPER_DEFAULT(1, "개발 기타"),
    PLANNER_DEFAULT(2, "기획 기타"),
    DESIGNER_DEFAULT(3, "디자인 기타"),

    // developer segmentation //
    DEVELOPER_OBJECTC(4, "Object-C"),
    DEVELOPER_SWIFT(5, "Swift"),
    DEVELOPER_PYTHON(6, "Python"),
    DEVELOPER_RUBY(7, "Ruby"),
    DEVELOPER_JAVA(8, "JAVA"),
    DEVELOPER_KOTLIN(9, "Kotlin"),
    DEVELOPER_C(10, "C/C++"),
    DEVELOPER_LUA(11, "Lua"),
    DEVELOPER_HTML(12, "HTML"),
    DEVELOPER_CSS(13, "CSS"),
    DEVELOPER_JAVASCRIPT(14, "Javascript"),
    DEVELOPER_PHP(15, "PHP"),
    DEVELOPER_GO(16, "GO"),
    DEVELOPER_NODE(17, "NodeJS"),
    DEVELOPER_SOLIDITY(18, "Solidity"),
    DEVELOPER_SOMPLICITY(19, "Somplicity"),
    DEVELOPER_LISP(20, "LISP"),
    DEVELOPER_R(21, "R"),
    DEVELOPER_PROLOG(22, "Prolog"),
    DEVELOPER_JULIA(23, "Julia"),

    // planner segmentation //
    PLANNER_STORYBOARD(24, "Storyboard"),
    PLANNER_DOCUMENTATION(25, "기획 문서화"),
    PLANNER_DATAANALYSIS(26, "데이터분석 기획"),
    PLANNER_GA(27, "GA"),
    PLANNER_MOBILEPLANNING(28, "모바일 기획"),
    PLANNER_WEB_PLANNING(29, "웹 기획"),
    PLANNER_PUBLISHING(30, "퍼블리싱"),

    // designer segmentation //
    DESIGNER_ADOBEXD(31, "어도비XD"),
    DESIGNER_SKETCH(32, "스케치"),
    DESIGNER_ZEPLIN(33, "Zeplin"),
    DESIGNER_PROTOIO(34, "Proto.io"),
    DESIGNER_FIGMA(35, "Figma"),
    DESIGNER_PHOTOSHOP(36, "포토샵"),
    DESIGNER_ILLUSTRATOR(37, "일러스트레이터");

    private final int skillCode;
    private final String skillName;

    Skill(int skillCode, String skillName) {
        this.skillCode = skillCode;
        this.skillName = skillName;
    }

    public static Skill of(int skillCode) {
        return Arrays.stream(Skill.values())
                .filter(v -> v.skillCode == skillCode)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_SKILL_CODE));
    }

    public static Skill of(String skillName) {
        return Arrays.stream(Skill.values())
                .filter(v -> v.skillName.equals(skillName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_SKILL_NAME));
    }
}
