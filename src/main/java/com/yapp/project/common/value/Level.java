package com.yapp.project.common.value;
import lombok.Getter;

@Getter
public enum Level {
    LEVEL1_1("새싹 1단계"),
    LEVEL1_2("새싹 2단계"),
    LEVEL1_3("새싹 3단계"),

    LEVEL2_1("묘목 1단계"),
    LEVEL2_2("묘목 2단계"),
    LEVEL2_3("묘목 3단계"),

    LEVEL3_1("나무 1단계"),
    LEVEL3_2("나무 2단계"),
    LEVEL3_3("나무 3단계"),

    LEVEL4_1("숲 1단계"),
    LEVEL4_2("숲 2단계"),
    LEVEL4_3("숲 3단계");

    private final String levelName;

    Level(String levelName) {
        this.levelName = levelName;
    }

    public static Level of(int score) {
        if (score >= 0 && score <= 3) {
            return LEVEL1_1;
        } else if (score >= 4 && score <= 6) {
            return LEVEL1_2;
        } else if (score >= 7 && score <= 9) {
            return LEVEL1_3;
        } else if (score >= 10 && score <= 19) {
            return LEVEL2_1;
        } else if (score >= 20 && score <= 29) {
            return LEVEL2_2;
        } else if (score >= 30 && score <= 39) {
            return LEVEL2_3;
        } else if (score >= 40 && score <= 49) {
            return LEVEL3_1;
        } else if (score >= 50 && score <= 59) {
            return LEVEL3_2;
        } else if (score >= 60 && score <= 69) {
            return LEVEL3_3;
        } else if (score >= 70 && score <= 79) {
            return LEVEL4_1;
        } else if (score >= 80 && score <= 89) {
            return LEVEL4_2;
        } else {
            return LEVEL4_3;
        }
    }
}
