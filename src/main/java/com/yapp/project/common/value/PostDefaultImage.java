package com.yapp.project.common.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum PostDefaultImage {
    IMAGE1("https://budi.s3.ap-northeast-2.amazonaws.com/post_image/default/dating.jpg"),
    IMAGE2("https://budi.s3.ap-northeast-2.amazonaws.com/post_image/default/education.jpg"),
    IMAGE3("https://budi.s3.ap-northeast-2.amazonaws.com/post_image/default/fashion.jpg"),
    IMAGE4("https://budi.s3.ap-northeast-2.amazonaws.com/post_image/default/finance.jpg"),
    IMAGE5("https://budi.s3.ap-northeast-2.amazonaws.com/post_image/default/game.jpg"),
    IMAGE6("https://budi.s3.ap-northeast-2.amazonaws.com/post_image/default/idea.jpg"),
    IMAGE7("https://budi.s3.ap-northeast-2.amazonaws.com/post_image/default/media.jpg"),
    IMAGE8("https://budi.s3.ap-northeast-2.amazonaws.com/post_image/default/medicine.jpg"),
    IMAGE9("https://budi.s3.ap-northeast-2.amazonaws.com/post_image/default/meeting.jpg"),
    IMAGE10("https://budi.s3.ap-northeast-2.amazonaws.com/post_image/default/network.jpg"),

    ;

    private final String imageUrl;

    public static List<String> getAllUrls(){
        final List<String> result = new ArrayList<>();

        for(var value : PostDefaultImage.values()){
            result.add(value.getImageUrl());
        }

        return result;
    }
}
