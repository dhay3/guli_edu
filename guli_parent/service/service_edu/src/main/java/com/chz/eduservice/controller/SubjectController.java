package com.chz.eduservice.controller;


import com.chz.eduservice.entity.vo.SubjectVo;
import com.chz.eduservice.service.SubjectService;
import com.chz.utils.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author chz
 * @since 2020-06-23
 */
@Api("类别接口,上传xslx接口")
@RestController
@CrossOrigin(origins = "http://localhost:9528")
@RequestMapping("/eduservice/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @ApiOperation(value = "添加课程分类", notes = "获取上传的文件,将上传文件的内容读取出来")
    @ApiImplicitParam(name = "上传的xslx文件", value = "对应name")
    @PostMapping
    public ResponseBo addSubjectByExcel(MultipartFile file) {
        subjectService.saveSubject(file, subjectService);
        return ResponseBo.ok();
    }

    @ApiOperation(value = "显示所有课程分类")
    @GetMapping
    public ResponseBo listSubject() {
        List<SubjectVo> subjectTree = subjectService.getAllOneTwoSubject();
        return ResponseBo.ok().data("list", subjectTree);
    }

    /**
     * 根据一级的title,获取所有的二级title
     *
     * @param title
     * @return
     */
    @GetMapping("/{title}")
    public ResponseBo getSubjectByTitle(@PathVariable String title) {
        List<String> list = subjectService.getSubTitlesByParentTitle(title);
        return ResponseBo.ok().data("titles", list);
    }
}

