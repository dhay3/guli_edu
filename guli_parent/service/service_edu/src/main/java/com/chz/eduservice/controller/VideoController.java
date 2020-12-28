package com.chz.eduservice.controller;


import com.chz.eduservice.entity.domain.Video;
import com.chz.eduservice.feign.VoDClientFeign;
import com.chz.eduservice.service.VideoService;
import com.chz.exception.CusException;
import com.chz.response.ResponseBo;
import com.chz.statuscode.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author chz
 * @since 2020-06-24
 */
@Api("视频小节接口")
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    /**
     * 注入feign
     */
    @Autowired
    private VoDClientFeign voDClientFeign;

    /**
     * 添加视频小节
     */
    @ApiOperation("添加视频小节")
    @PostMapping
    public ResponseBo saveVideo(@RequestBody Video video) {
        return videoService.save(video) ? ResponseBo.ok() : ResponseBo.error();
    }

    /**
     * 修改视频小节
     */
    @PutMapping
    public ResponseBo updateVideo(@RequestBody Video video) {
        return videoService.updateById(video) ? ResponseBo.ok() : ResponseBo.error();
    }

    /**
     * 删除小节
     * 包括数据库中的和阿里云上的
     * 这里的videoId指的时数据库中video的主键id
     */
    @ApiOperation("删除小节,包括数据库和aliyun中的数据")
    @ApiImplicitParam(name = "videoId",
            value = "这里的videoId指的时数据库中video的主键id,并不是aliyun中的id")
    @DeleteMapping("/{videoId}")
    public ResponseBo deleteVideo(@PathVariable String videoId) {
        //根据小节id,获取视频id,然后调用删除
        Video video = videoService.getById(videoId);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            //远程调用删除视频, 这里能拿到api返回的值
            //这里如果服务的熔断器被调用,返回的结果就是error
            ResponseBo res = voDClientFeign.deleteVoDByVideoSourceId(videoSourceId);
            if (res.getCode() == ResultCode.ERROR.val()) {
                throw new CusException("熔断器触发, 删除视频失败", ResultCode.ERROR.val());
            }
        }
        videoService.removeById(videoId);
        return ResponseBo.ok();
    }

    /**
     * 根据videoId查询video属性
     */
    @ApiOperation("根据videoId查询video")
    @GetMapping("/{videoId}")
    public ResponseBo getVideoById(@PathVariable String videoId) {
        Video video = videoService.getById(videoId);
        return ResponseBo.ok().data("video", video);
    }
}

