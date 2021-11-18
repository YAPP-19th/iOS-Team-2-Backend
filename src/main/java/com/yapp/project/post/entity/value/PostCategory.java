package com.yapp.project.post.entity.value;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PostCategory {
    DEFAULT(0, "기타"),
    O2O(1, "O2O"),
    SHARING_SERVICE(2, "공유 서비스"),
    DATING_SERVICE(3, "데이팅 서비스"),
    TRAVEL(4, "여행"),
    SOCIAL_NETWORK(5, "소셜네트워크"),
    BEAUTY_FASHION(6, "뷰티/패션"),
    E_COMMERCE(7, "이커머스"),
    ENTERTAINMENT(8, "엔터테인먼트"),
    GAME(9, "게임"),
    HEALTH_SPORTS(10, "헬스/스포츠"),
    NEWS_INFORMATION(11, "뉴스/정보"),
    UTILITY(12, "유틸"),
    FINANCE(13, "금융"),
    PROPERTY_INTERIOR(14, "부동산/인테리어"),
    RELIGION(15, "종교"),
    SPACE_ALIEN(16, "우주/외계인");

    private final int categoryCode;
    private final String categoryName;

    PostCategory(int categoryCode, String categoryName) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
    }

    public static PostCategory of(int categoryCode) {
        return Arrays.stream(PostCategory.values())
                .filter(v -> v.categoryCode == categoryCode)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_CATEGORY_CODE));
    }

    public static PostCategory of(String categoryName) {
        return Arrays.stream(PostCategory.values())
                .filter(v -> v.categoryName.equals(categoryName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_CATEGORY_NAME));
    }
}
