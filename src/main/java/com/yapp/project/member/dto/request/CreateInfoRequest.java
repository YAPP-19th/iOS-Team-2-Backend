package com.yapp.project.member.dto.request;

import com.yapp.project.common.exception.DtoValidationFailMessage;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder
public class CreateInfoRequest {
    @Size(min = 2, max = 12, message = DtoValidationFailMessage.INVALID_NICKNAME)
    private String nickName;

    @Size(min = 2, message = DtoValidationFailMessage.INVALID_ADDRESS)
    private String memberAddress;

    @Pattern(regexp = "^[0-9|ㄱ-ㅎ|가-힣|a-z|A-Z|]{2,15}$", message = DtoValidationFailMessage.INVALID_DESCRIPTION)
    private String description;

    private int basePosition;

    private List<String> portfolioLink;

    private List<String> positionList;

    private List<CareerRequest> careerList;

    private List<ProjectRequest> projectList;

}
