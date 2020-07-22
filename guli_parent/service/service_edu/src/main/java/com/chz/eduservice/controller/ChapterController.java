package com.chz.eduservice.controller;


import com.chz.eduservice.entity.domain.Chapter;
import com.chz.eduservice.entity.vo.ChapterVo;
import com.chz.eduservice.service.ChapterService;
import com.chz.response.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author chz
 * @since 2020-06-25
 */
@Api("章节接口")
@RestController
@CrossOrigin
@RequestMapping("/eduservice/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @ApiOperation(value = "获取所有课程对应的章节和章节对应的视频信息", notes = "通过前端vue路由过来的id")
    @ApiImplicitParam(name = "courseId", value = "路由过来的id")
    @GetMapping("/{courseId}")
    //这里的@PathVariable("courseId")可以不写也可以写,本项目统一写
    public ResponseBo getChapterAndVideoById(@PathVariable("courseId") String courseId) {
        List<ChapterVo> chapters = chapterService.getChapterAndVideoById(courseId);
        return ResponseBo.ok().data("chapters", chapters);
    }

    @ApiOperation("添加章节")
    @PostMapping
    public ResponseBo saveChapter(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return ResponseBo.ok();
    }

    @ApiOperation("修改章节")
    @PutMapping
    public ResponseBo updateChapter(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return ResponseBo.ok();
    }

    @ApiOperation(value = "删除章节", notes = "如果章节下有小节不会被删除")
    @DeleteMapping("/{chapterId}")
    public ResponseBo deleteChapterById(@PathVariable String chapterId) {
        return chapterService.deleteChapterOnCasecade(chapterId) ?
                ResponseBo.ok() : ResponseBo.error();
    }

    @ApiOperation("根据章节id查询章节信息")
    @GetMapping("/callback/{chapterId}")
    public ResponseBo getChapterById(@PathVariable String chapterId) {
        Chapter chapter = chapterService.getById(chapterId);
        return ResponseBo.ok().data("chapter", chapter);
    }

}

