package com.yapp.project.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BudiMemberResponse {
    private Long id;
    private String imgUrl;
    private String nickName;
    private String address;

    private String introduce;
    private List<String> position;
}
