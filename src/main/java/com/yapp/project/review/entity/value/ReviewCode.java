package com.yapp.project.review.entity.value;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.IllegalRequestException;
import com.yapp.project.common.exception.type.NotFoundException;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum ReviewCode {
    // positive //
    POSITIVE1(1, "성실하게 참여했어요."),
    POSITIVE2(2, "적극적으로 참여했어요."),
    POSITIVE3(3, "책임감이 있어요."),
    POSITIVE4(4, "커뮤니케이션 능력이 좋아요."),

    POSITIVE5(5, "실력이 뛰어나요."),
    POSITIVE6(6, "아이디어가 풍부해요"),
    POSITIVE7(7, "문제 상황을 잘 해결해요."),

    POSITIVE8(8, "친화력이 좋아요."),
    POSITIVE9(9, "친절하고 매너가 좋아요."),

    // negative //
    NEGATIVE1(-1, "책임감이 없어요."),
    NEGATIVE2(-2, "적극적으로 참여하지 않아요."),
    NEGATIVE3(-3, "커뮤니케이션 하기 어려워요."),
    NEGATIVE4(-4, "시간약속을 잘 안지켜요."),
    NEGATIVE5(-5, "연락이 안돼요."),

    NEGATIVE6(-6, "실력이 기대 이하에요."),
    NEGATIVE7(-7, "문제 상황을 제대로 해결하지 않아요."),

    NEGATIVE8(-8, "불친절해요."),
    NEGATIVE9(-9, "분위기를 흐려요."),

    ;

    private final int code;
    private final String name;

    ReviewCode(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ReviewCode of(int reviewCode) {
        return Arrays.stream(ReviewCode.values())
                .filter(v -> v.code == reviewCode)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_REVIEW_CODE));
    }

    public static ReviewCode of(String reviewName) {
        return Arrays.stream(ReviewCode.values())
                .filter(v -> v.name.equals(reviewName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_REVIEW_NAME));
    }

    public static List<String> getAllPositiveReviewNames() {
        final List<String> result = new ArrayList<>();

        for(var value : ReviewCode.values()){
            if(value.getCode() > 0)
                result.add(value.getName());
        }

        return result;
    }

    public static List<String> getAllNegativeReviewNames() {
        final List<String> result = new ArrayList<>();

        for(var value : ReviewCode.values()){
            if(value.getCode() < 0)
                result.add(value.getName());
        }
        return result;
    }

    public static void validateIsExistReviewOrElseThrow(String reviewStr) {
        for(var reviewCode : ReviewCode.values()){
            if(reviewCode.name.equals(reviewStr)) return;
        }

        throw new IllegalRequestException(ExceptionMessage.NOT_EXIST_SELECT_REVIEW);
    }
}
