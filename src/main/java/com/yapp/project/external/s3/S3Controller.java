package com.yapp.project.external.s3;

import com.yapp.project.common.exception.ExceptionMessage;
import com.yapp.project.common.exception.type.IllegalRequestException;
import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import com.yapp.project.config.PropertyProvider;
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

    private final PropertyProvider propertyProvider;

    @ApiOperation("단건 이미지 URL로 변환(프로젝트 이미지, 사용자 프로필 이미지 통합)")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult> uploadPostImage(@ModelAttribute MultipartFile image) throws IOException {

        if(image == null || image.isEmpty()){
            throw new IllegalRequestException(ExceptionMessage.INVALID_IMAGE_EXCEPTION);
        }
        
        String uploadedImageUrl = s3Uploader.upload(image, propertyProvider.getIMAGE_DIR());

        Map response = new HashMap<String, String>();
        response.put("imageUrl", uploadedImageUrl);

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }

}
