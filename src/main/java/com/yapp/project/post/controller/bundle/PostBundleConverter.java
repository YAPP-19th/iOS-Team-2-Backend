package com.yapp.project.post.controller.bundle;

import com.yapp.project.post.dto.request.RecruitingPositionRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostBundleConverter {
    public List<RecruitingPositionBundle> toTeamMemberRequestBundle(List<RecruitingPositionRequest> requests){
        List<RecruitingPositionBundle> bundle = new ArrayList<>();

        for(var request : requests){
            bundle.add(new RecruitingPositionBundle(request.getPositionCode(), request.getSkillCode(), request.getRecruitingNumber()));
        }

        return bundle;
    }
}
