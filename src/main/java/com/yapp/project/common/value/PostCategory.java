package com.yapp.project.common.value;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.NotFoundException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private final int code;
    private final String name;

    PostCategory(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PostCategory of(int categoryCode) {
        return Arrays.stream(PostCategory.values())
                .filter(v -> v.code == categoryCode)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_CATEGORY_CODE));
    }

    public static PostCategory of(String categoryName) {
        return Arrays.stream(PostCategory.values())
                .filter(v -> v.name.equals(categoryName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_EXIST_POST_CATEGORY_NAME));
    }

    public static List<String> getAll(){
        final List<String> result = new ArrayList<>();

        for(var value : PostCategory.values()){
            result.add(value.getName());
        }

        return result;
    }

}
