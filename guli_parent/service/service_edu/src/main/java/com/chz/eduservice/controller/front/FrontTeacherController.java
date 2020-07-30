package com.chz.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chz.eduservice.entity.domain.Course;
import com.chz.eduservice.entity.domain.Teacher;
import com.chz.eduservice.service.CourseService;
import com.chz.eduservice.service.TeacherService;
import com.chz.response.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/26 16:30
 * @Description: TODO
 */
@Api("前端讲师模块")
@RestController
@Slf4j
@RequestMapping("/eduservice/front/teacher")
public class FrontTeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "前端讲师分页", notes = "8条记录为一页")
    @GetMapping("/{currentPage}/{pageSize}")
    private ResponseBo pageTeachersFront(@PathVariable Integer currentPage,
                                         @PathVariable Integer pageSize) {
        Page<Teacher> page = new Page<>(currentPage, pageSize);
        Map<String, Object> map = teacherService.pageTeachersFront(page);
        return ResponseBo.ok().data(map);
    }

    @ApiOperation(value = "查询单个讲师", notes = "讲师详情包括教学课程")
    @GetMapping("/{teacherId}")
    private ResponseBo getTeacherDetailsById(@PathVariable String teacherId) {
        Teacher teacher = teacherService.getOne(new QueryWrapper<Teacher>().lambda()
                .eq(Teacher::getId, teacherId));
        List<Course> courses =  courseService.list(new QueryWrapper<Course>().lambda()
        .eq(Course::getTeacherId,teacherId));
        return ResponseBo.ok().data("teacher", teacher).data("courses",courses);
    }
}
