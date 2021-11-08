package com.yapp.project.post.entity.vo;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PostCategory {
    DEFAULT(0),
    O2O(1),
    SHARING_SERVICE(2),
    DATING_SERVICE(3),
    TRAVEL(4),
    SOCIAL_NETWORK(5),
    BEAUTY_FASHION(6),
    E_COMMERCE(7),
    ENTERTAINMENT(8),
    GAME(9),
    HEALTH_SPORTS(10),
    NEWS_INFORMATION(11),
    UTILITY(12),
    FINANCE(13),
    PROPERTY_INTERIOR(14),
    RELIGION(15),
    SPACE_ALIEN(16);

    private final int categoryCode;

    PostCategory(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public static PostCategory of(int categoryCode) {
        return Arrays.stream(PostCategory.values())
                .filter(v -> v.categoryCode == categoryCode)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_CATEGORY_CODE));
    }
}
