package com.chz.eduservice.controller;


import com.chz.eduservice.entity.domain.Video;
import com.chz.eduservice.service.VideoService;
import com.chz.utils.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin
@RequestMapping("/eduservice/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

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
     */
    @ApiOperation("删除小节")
    @DeleteMapping("/{videoId}")
    public ResponseBo deleteVideo(@PathVariable String videoId) {
        return videoService.removeById(videoId) ? ResponseBo.ok() : ResponseBo.error();
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

