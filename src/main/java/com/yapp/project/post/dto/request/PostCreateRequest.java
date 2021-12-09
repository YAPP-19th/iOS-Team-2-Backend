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
public class PostCreateRequest {
    @NotBlank(message = DtoValidationFailMessage.EMPTY_POST_IMAGE_URL)
    private String imageUrl;

    @ApiModelProperty(example = "title example")
    @NotBlank(message = DtoValidationFailMessage.INVALID_POST_TITLE)  //regexp = "^\\S{2,255}$",
    private String title;

    @ApiModelProperty(example = "여행")
    @NotBlank(message = DtoValidationFailMessage.INVALID_CATEGORY)
    private String categoryName;

    @ApiModelProperty(example = "'2021-12-31T23:59:59'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @ApiModelProperty(example = "'2022-12-31T23:59:59'")
    @NotNull
    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @ApiModelProperty(example = "용인시 수지구 죽전동")
    @NotBlank(message = DtoValidationFailMessage.INVALID_REGION)
    private String region;

    @ApiModelProperty(example = "description detail example")
    @NotBlank(message = DtoValidationFailMessage.INVALID_DESCRIPTION)  //regexp = "^\\S{2,21844}$",
    private String description;

    @ApiModelProperty(example = "'{미정|온라인|오프라인|온오프라인} 넷 중 택1'")
    @Pattern(regexp = "^[가-힣]*$", message = DtoValidationFailMessage.INVALID_POST_ONLINE_INFO)
    private String onlineInfo;

    private List<RecruitingPositionRequest> recruitingPositions;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RecruitingPositionRequest {
        @ApiModelProperty(example = "iOS 개발")
        @NotBlank(message = DtoValidationFailMessage.INVALID_POSITION_NAME)
        private String positionName;

        @ApiModelProperty(example = "2")
        @Min(value = 1, message = DtoValidationFailMessage.INVALID_RECRUITING_NUMBER_NUMBER)
        private int recruitingNumber;
    }
}
