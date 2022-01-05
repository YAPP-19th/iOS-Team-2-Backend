package com.yapp.project.info.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostStatusResponse {
    private String postStatusName;

    private int postStatusCode;
}
