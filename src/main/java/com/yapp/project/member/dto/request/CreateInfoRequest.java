package com.yapp.project.member.dto.request;

import com.yapp.project.common.exception.DtoValidationFailMessage;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder
public class CreateInfoRequest {
    @Size(min = 2, max = 12, message = DtoValidationFailMessage.INVALID_NICKNAME)
    private String nickName;

    @Size(min = 2, message = DtoValidationFailMessage.INVALID_ADDRESS)
    private String memberAddress;

    @Size(min = 2, max = 255, message = DtoValidationFailMessage.INVALID_POST_TITLE)
    private String description;

    @Min(value = 1, message = DtoValidationFailMessage.INVALID_POSITION_CODE)
    private int basePosition;

    private List<String> portfolioLink;

    private List<String> positionList;

    private List<CareerRequest> careerList;  //TODO: 삭제, -> 스코어 계산에서도 삭제

    private List<ProjectRequest> projectList;

}
