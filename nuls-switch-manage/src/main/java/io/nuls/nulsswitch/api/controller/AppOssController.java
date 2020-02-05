package io.nuls.nulsswitch.api.controller;

import io.nuls.nulsswitch.common.annotation.Log;
import io.nuls.nulsswitch.common.type.EnumErrorCode;
import io.nuls.nulsswitch.common.utils.Result;
import io.nuls.nulsswitch.oss.service.FileService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <pre>
 *  基于jwt实现的API测试类
 * </pre>
 *
 *
 */
@RestController
@RequestMapping("/api/oss/")
@AllArgsConstructor
public class AppOssController {

    private FileService fileService;

    @Log("上传文件")
    @ResponseBody
    @PostMapping("/upload")
    @ApiImplicitParams({ @ApiImplicitParam(name = "Authorization", value = "Authorization", paramType = "header") })
    Result<String> upload(@RequestParam("file") MultipartFile file) {
        String url = "";
        try {
            url = fileService.upload(file.getBytes(), file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            return Result.build(EnumErrorCode.FileUploadGetBytesError.getCode(), EnumErrorCode.FileUploadGetBytesError.getMsg());
        }
        return Result.ok(url);
    }

}
