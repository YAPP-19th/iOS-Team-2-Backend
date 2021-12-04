package com.yapp.project.external.s3;

import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/images", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "이미지 URL 변환")
public class S3Controller {
    private final S3Uploader s3Uploader;

    private final String POST_IMAGE_DIR = "post_image";
    private final String USER_IMAGE_DIR = "user_profile_image";

    @ApiOperation("단건 이미지 URL로 변환(게시글 이미지)")
    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult> uploadPostImage(@ModelAttribute MultipartFile image) throws IOException {

        String uploadedImageUrl = s3Uploader.upload(image, POST_IMAGE_DIR);

        Map response = new HashMap<String, String>();
        response.put("imageUrl", uploadedImageUrl);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation("단건 이미지 URL로 변환(사용자 이미지)")
    @PostMapping(value = "/members", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult> uploadMemberImages(@ModelAttribute MultipartFile image) throws IOException {

        String uploadedImageUrl = s3Uploader.upload(image, USER_IMAGE_DIR);

        Map response = new HashMap<String, String>();
        response.put("imageUrl", uploadedImageUrl);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

}
