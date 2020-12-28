package com.chz.oss.controller;

import com.chz.oss.service.IossService;
import com.chz.response.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api("oss控制器")
@RestController
//@CrossOrigin(origins = "http://localhost:9528")
@RequestMapping("/eduoss/fileoss")
public class OssController {
    @Autowired
    private IossService ossService;

    /**
     * 上传头像方法
     */
    @ApiOperation(value = "上传xslx文件到oss", notes = "uploadFileAvatar返回上传成功后的url")
    @PostMapping
    public ResponseBo uploadOss(MultipartFile file) throws IOException {
        //返回上传到oss上的url路径
        String url = ossService.uploadFileAvatar(file);
        return ResponseBo.ok().data("url", url);
    }
}
