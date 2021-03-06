package com.chz.vod.controller;

import com.aliyuncs.exceptions.ClientException;
import com.chz.exception.CusException;
import com.chz.response.ResponseBo;
import com.chz.statuscode.ResultCode;
import com.chz.vod.service.VoDService;
import com.chz.vod.util.VoDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/17 21:04
 * @Description: TODO
 */
@Api("VoD控制器")
@RestController
@RequestMapping("/eduvod/video")
public class VoDController {
    @Autowired
    private VoDService voDService;

    /**
     * 上传视频到阿里云
     */
    @PostMapping
    public ResponseBo uploadByVoD(MultipartFile file) {
        //存视频id到数据库, 因为视频地址会加密
        String videoId = voDService.uploadByVoD(file);
        return ResponseBo.ok().data("videoId", videoId);
    }

    /**
     * 根据视频id删除aliyun中的视频
     * 这里的videoId时aliyun上的id同时也是数据库中
     *
     * @return
     */
    @DeleteMapping("/{videoSourceId}")
    public ResponseBo deleteVoDByVideoSourceId(@PathVariable String videoSourceId) {
        try {
            voDService.deleteVoDByVideoSourceId(videoSourceId);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new CusException("删除视频失败", ResultCode.ERROR.val());
        }
        return ResponseBo.ok();
    }

    /**
     * 批量删除aliyun上的视频, 用于删除课程同时删除视频
     *
     * @param videoSourceIds
     * @return
     */
    @DeleteMapping("/deleteBatch")
    public ResponseBo deleteBatchVoDByVideoSourceId(@RequestParam("videoSourceIds") List<String> videoSourceIds) {
        try {
            voDService.deleteBatchVoDByVideoSourceId(videoSourceIds);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new CusException("删除视频失败", ResultCode.ERROR.val());
        }
        return ResponseBo.ok();
    }

    @ApiOperation(value = "根据视频id获取相关视频playauth", notes = "注意playAuth会变化")
    @GetMapping("/{videoId}")
    public ResponseBo getPlayAuth(@PathVariable String videoId) {
        System.out.println(videoId);
        String playAuth = null;
        try {
            playAuth = VoDUtil.getPlayAuth(videoId);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new CusException("获取视频playAuth错误", ResultCode.ERROR.val());
        }
        return ResponseBo.ok().data("playAuth", playAuth);
    }
}
