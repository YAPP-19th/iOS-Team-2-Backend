package com.yapp.project.post.dto.request;

import com.yapp.project.common.exception.DtoValidationFailMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class PostUpdateRequest {
    @NotBlank(message = DtoValidationFailMessage.EMPTY_POST_IMAGE_URL)
    private String imageUrl;

    @ApiModelProperty(example = "title example")
    @NotBlank(message = DtoValidationFailMessage.INVALID_POST_TITLE)
    private String title;

    @ApiModelProperty(example = "여행")
    @NotBlank(message = DtoValidationFailMessage.INVALID_CATEGORY)
    private String categoryName;

    @ApiModelProperty(example = "'2021-12-31 23:59:59'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @ApiModelProperty(example = "'2022-12-31 23:59:59'")
    @NotNull
    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @ApiModelProperty(example = "용인시 수지구 죽전동")
    @NotBlank(message = DtoValidationFailMessage.INVALID_REGION)
    private String region;

    @ApiModelProperty(example = "description detail example")
    @NotBlank(message = DtoValidationFailMessage.INVALID_DESCRIPTION)
    private String description;

    @ApiModelProperty(example = "'{미정|온라인|오프라인|온오프라인} 넷 중 택1'")
    @Pattern(regexp = "^[가-힣]*$", message = DtoValidationFailMessage.INVALID_POST_ONLINE_INFO)
    private String onlineInfo;

    private List<PostCreateRequest.RecruitingPositionRequest> recruitingPositions;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RecruitingPositionRequest {
        @ApiModelProperty(example = "AI 개발")
        @NotBlank(message = DtoValidationFailMessage.INVALID_POSITION_NAME)
        private String positionName;

        @ApiModelProperty(example = "3")
        @Min(value = 1, message = DtoValidationFailMessage.INVALID_RECRUITING_NUMBER_NUMBER)
        private int recruitingNumber;
    }
}
