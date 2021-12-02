package com.yapp.project.info;

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
@RequestMapping(value = "/api/v1/info")
@Api(tags = "Info")
public class InfoController {
    private final InfoService infoService;
    @ApiOperation(value = "직무 리스트", notes = "개발 / 기획 / 디자인")
    @GetMapping(value = "/getPositionList")
    public ResponseEntity<ApiResult> getPositionList(@RequestParam("position") String position) {
        List list = infoService.getPostionInfo(position);
        return ResponseEntity.ok(
                ApiResult.of(ResponseMessage.SUCCESS, list)
        );
    }
}
