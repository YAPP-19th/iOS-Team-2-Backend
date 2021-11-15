package com.yapp.project.post.dto.request;

import com.yapp.project.common.exception.DtoValidationFailMessage;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter  //@ModelAttribute를 이용해 파라미터 값을 DTO에 한 번에 바인딩 하기 위함
public class PostCreateRequest {
    @Pattern(regexp = "^\\S{2,255}$", message = DtoValidationFailMessage.INVALID_POST_TITLE)
    private String title;

    @NotBlank(message = DtoValidationFailMessage.INVALID_CATEGORY)
    private String categoryName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @NotNull
    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @NotBlank(message = DtoValidationFailMessage.INVALID_REGION)
    private String region;

    @Pattern(regexp = "^\\S{2,21844}$", message = DtoValidationFailMessage.INVALID_DESCRIPTION)
    private String description;

    @Positive(message = DtoValidationFailMessage.INVALID_OWNER_ID)
    private Long ownerId;

    @Size(min = 0, max = 5)
    private List<MultipartFile> postImages;

    @Size(min = 0)
    private List<RecruitingPositionRequest> recruitingPositionRequests;
}
