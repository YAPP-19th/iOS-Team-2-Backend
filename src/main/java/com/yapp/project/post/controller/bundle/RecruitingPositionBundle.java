package com.yapp.project.post.controller.bundle;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecruitingPositionBundle {
    private final String positionName;

    private final String skillName;

    private final int recruitingNumber;
}
