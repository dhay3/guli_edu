package com.chz.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chz.eduservice.entity.domain.Course;
import com.chz.eduservice.entity.vo.CourseInfoVo;
import com.chz.eduservice.entity.vo.CoursePublishInfoVo;
import com.chz.eduservice.entity.vo.CourseQuery;
import com.chz.eduservice.service.CourseService;
import com.chz.utils.ResponseBo;
import com.chz.utils.statuscode.CourseStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.HashMap;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author chz
 * @since 2020-06-24
 */
@Api("课程接口")
@Validated
@RestController
@CrossOrigin//接收所有跨域请求
@RequestMapping("/eduservice/course")
public class CourseController {
    @Autowired
    private CourseService courseService;


    @ApiOperation(value = "添加课程", notes = "使用json串传递对象")
    //如果使用@RequestBody swagger要将封装对象中的每一个值都写出,否则只会显示一个值
//    @ApiImplicitParam(name = "courseInfoVo", value = "添加课程的vo封装对象")
    @PostMapping
    public ResponseBo saveCourse(@RequestBody CourseInfoVo courseInfoVo) {
        //返回添加课程后的id,为了之后确认调用id
        String id = courseService.addCourseInfo(courseInfoVo);
        return ResponseBo.ok().data("courseId", id);
    }

    @ApiOperation(value = "根据课程id查询对应结果", notes = "将查询出来的结果返回给前端,以便回显数据")
    @GetMapping("/{id}")
    public ResponseBo getCourseById(@PathVariable("id") String courseId) {
        CourseInfoVo courseInfo = courseService.getCourseInfoById(courseId);
        return ResponseBo.ok().data("courseInfoVo", courseInfo);
    }

    @ApiOperation("修改课程信息")
    @PutMapping
    public ResponseBo updateCourse(@RequestBody CourseInfoVo courseInfoVo) {
        return courseService.updateCourseById(courseInfoVo) ? ResponseBo.ok() : ResponseBo.error();
    }

    @ApiOperation("根据id查询对应course的所有信息")
    @GetMapping("/all/{id}")
    public ResponseBo getCourseAllInfoById(@PathVariable String id) {
        CoursePublishInfoVo courseAllInfo = courseService.getCourseAllInfoById(id);
        return ResponseBo.ok().data("courseAllInfo", courseAllInfo);
    }

    /**
     * 前面几步已经存入数据库, 最后只需要修改课程状态
     */
    @ApiOperation(value = "提交最终信息, 修改信息", notes = "课程状态 Draft未发布  Normal已发布")
    @PostMapping("/publish/{id}")
    public ResponseBo publishCourse(@PathVariable String id) {
        Course course = new Course();
        //设置课程发布状态, 使用枚举类控制细粒度, 枚举类能与数据库中的String类型互相转换
        course.setStatus(CourseStatus.Normal);
//        course.setStatus(CourseStatus.Normal.toString());
        course.setId(id);
        courseService.updateById(course);
        return ResponseBo.ok();
    }

    /**
     * 根据指定信息分页查询用户
     * 这里@Min(1)只作为提示, sql异常会优先抛出
     * 注意get请求没有body,因为@RequestBody所以接收的请求必须是post
     *
     * @param curPage     jsr303 校验最小值为1
     * @param pageSize
     * @param courseQuery
     * @return
     */
    @ApiOperation(value = "分页查询")
    @PostMapping("/{curPage}/{pageSize}")
    public ResponseBo pageCourseOnCondition(@Min(1) @PathVariable Integer curPage,
                                            @PathVariable Integer pageSize,
                                            @RequestBody CourseQuery courseQuery) {
        HashMap<String, Object> map = new HashMap<>();
        Page<CoursePublishInfoVo> page = courseService.pageCourseAllInfo(curPage, pageSize, courseQuery);
        map.put("courses",page.getRecords());
        map.put("total",page.getTotal());
        return ResponseBo.ok().data("info", map);
    }
    @DeleteMapping("/{courseId}")
    public ResponseBo deleteCourseById(@PathVariable String courseId){
        courseService.removeCourseById(courseId);
        return ResponseBo.ok();
    }


}

