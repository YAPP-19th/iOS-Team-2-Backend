package com.yapp.project.post.dto.request;

import com.yapp.project.common.exception.DtoValidationFailMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Builder
@Getter
public class PostUpdateRequest {
    @NotBlank(message = DtoValidationFailMessage.INVALID_URL)
    private String imageUrl;

    @ApiModelProperty(example = "to be updated title example")
    @Size(min = 2, max = 255, message = DtoValidationFailMessage.INVALID_POST_TITLE)
    private String title;

    @ApiModelProperty(example = "여행")
    @NotBlank(message = DtoValidationFailMessage.INVALID_CATEGORY)
    private String categoryName;

    @ApiModelProperty(example = "'2021-12-31 23:59:59'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @ApiModelProperty(example = "'2022-12-31 23:59:59'")
    @NotNull(message = DtoValidationFailMessage.INVALID_TIME)
    @FutureOrPresent(message = DtoValidationFailMessage.INVALID_TIME)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @ApiModelProperty(example = "용인시 수지구 죽전동")
    @NotBlank(message = DtoValidationFailMessage.INVALID_REGION)
    private String region;

    @ApiModelProperty(example = "to be updated description detail example")
    @Size(min = 2, max = 1000, message = DtoValidationFailMessage.INVALID_DESCRIPTION)
    private String description;

    @ApiModelProperty(example = "'{미정|온라인|오프라인|온오프라인} 넷 중 택1'")
    @NotBlank(message = DtoValidationFailMessage.INVALID_POST_ONLINE_INFO)
    private String onlineInfo;
}
