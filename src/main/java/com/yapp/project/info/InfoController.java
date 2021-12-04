package com.yapp.project.info;

import com.yapp.project.common.value.PostDefaultImage;
import com.yapp.project.common.web.ApiResult;
import com.yapp.project.common.web.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/infos")
@Api(tags = "Info")
public class InfoController {
    private final InfoService infoService;

    @ApiOperation(value = "직무 리스트", notes = "개발 / 기획 / 디자인")
    @GetMapping(value = "/positions")
    public ResponseEntity<ApiResult> getPositionList(@RequestParam("position") String position) {
        List list = infoService.getPostionInfo(position);
        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, list)
        );
    }

    @ApiOperation(value = "기본 게시글 이미지 URL", notes = "모든 게시글 디폴트 이미지 URL")
    @GetMapping(value = "/postDefaultImageUrls")
    public ResponseEntity<ApiResult> getAllPostDefaultImageUrls() {
        var response = PostDefaultImage.getAllUrls();

        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, response)
        );
    }
}
