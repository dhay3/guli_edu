package com.chz.eduservice.controller.front;

import com.chz.eduservice.entity.domain.Course;
import com.chz.eduservice.entity.domain.Teacher;
import com.chz.eduservice.service.CourseService;
import com.chz.eduservice.service.TeacherService;
import com.chz.response.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/20 20:43
 * @Description: TODO
 */
@Api("处理用户界面发送到后端的请求")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/front")
public class FrontIndexController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @ApiOperation("查询前8热门课程,查询前四讲师")
    @GetMapping
    public ResponseBo getTopDESC() {
        List<Teacher> teachers = teacherService.getTopTeachersDESC();
        List<Course> courses = courseService.getTopCoursesDESC();
        return ResponseBo.ok().data("teachers", teachers).data("courses", courses);
    }


}
