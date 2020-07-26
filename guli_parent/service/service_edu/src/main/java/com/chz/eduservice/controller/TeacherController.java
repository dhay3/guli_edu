package com.chz.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chz.eduservice.entity.domain.Teacher;
import com.chz.eduservice.entity.vo.TeacherQuery;
import com.chz.eduservice.service.TeacherService;
import com.chz.response.ResponseBo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author chz
 * @since 2020-05-21
 */
@Api("教师接口")
@Validated
@RestController
//允许跨域请求,通过配置类统一配置
//@CrossOrigin(origins = "http://localhost:9528")
@RequestMapping("/eduservice/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping(produces = "application/json;charset=utf-8")
    @ApiOperation(value = "查询所有讲师",
            notes = "produces对应request的accept,方法产生的数据将是json格式,且防止中文乱码")
    public ResponseBo list() {
        List<Teacher> teachers = teacherService.list();
        //list返回json形式就是json数组形式
        return ResponseBo.ok().data("teachers", teachers);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查询单个讲师")
    @ApiImplicitParam(name = "id", value = "讲师id",
            required = true, dataTypeClass = String.class, paramType = "path")
    public ResponseBo get(@PathVariable("id") String id) {
        Teacher teacher = teacherService.getOne(new QueryWrapper<Teacher>().lambda()
                .eq(Teacher::getId, id));
        return ResponseBo.ok().data("teacher", teacher);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "逻辑删除讲师", notes = "0未删除,1删除")
    @ApiImplicitParam(name = "id", value = "讲师id",
            required = true, dataTypeClass = String.class, paramType = "path")
    @ApiResponses({
            //可能返回多种状态码
            @ApiResponse(code = 20000, message = "错误"),
            @ApiResponse(code = 20001, message = "成功")
    })
    public ResponseBo delete(@PathVariable("id") String id) {
        return teacherService.remove(new QueryWrapper<Teacher>()
                .lambda().eq(Teacher::getId, id)) ? ResponseBo.ok() : ResponseBo.error();
    }

    @GetMapping("/pageTeacher/{current}/{size}")
    @ApiOperation(value = "分页查询讲师")
    @ApiImplicitParams({
            //注意的一点是要与@PathVarible指定的参数相同,否则swagger会出现两个不同的参数
            @ApiImplicitParam(name = "current", value = "当前页",
                    required = true, dataTypeClass = Integer.class, paramType = "path"),
            @ApiImplicitParam(name = "size", value = "页面大小",
                    required = true, dataTypeClass = Integer.class, paramType = "path")
    })
    public ResponseBo pageListTeacher(@PathVariable("current") Integer currentPage,
                                      @PathVariable("size") Integer pageSize) {
        IPage<Teacher> teacherPage = new Page<>(currentPage, pageSize);
        //page方法会把结果封装回teacherPage
        teacherService.page(teacherPage, null);
        //获取所有的记录条数
        long total = teacherPage.getTotal();
        List<Teacher> teachers = teacherPage.getRecords();
        return ResponseBo.ok().data("total", total).data("teachers", teachers);
    }

    @PostMapping("/pageTeacherCondition/{current}/{size}")
    @ApiOperation(value = "条件分页", notes = "TeacherQuery条件封装类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页",
                    dataTypeClass = Integer.class, required = true, paramType = "path"),
            @ApiImplicitParam(name = "size", value = "页面大小",
                    dataTypeClass = Integer.class, required = true, paramType = "path"),
            @ApiImplicitParam(name = "teacherQuery", value = "条件封装类",
                    dataTypeClass = TeacherQuery.class, required = false, paramType = "body"),
    })
    public ResponseBo pageTeacherCondition(@Min(1) @PathVariable("current") Integer currentPage,
                                           @PathVariable("size") Integer pageSize,
                                           @RequestBody(required = false) TeacherQuery teacherQuery) {

        IPage<Teacher> iPage = teacherService.pageTeacherCondition(currentPage, pageSize, teacherQuery);
        List teachers = iPage.getRecords();
        //total返回的因该是所有符合条件的结果,而不是limit后的返回的结果条数
        long total = iPage.getTotal();
        return ResponseBo.ok().data("total", total).data("teachers", teachers);
    }

    @PostMapping
    @ApiOperation(value = "添加讲师")
    @ApiImplicitParam(name = "teacher", value = "讲师",
            dataTypeClass = Teacher.class, required = true, paramType = "body")
    public ResponseBo save(@RequestBody Teacher teacher) {
        return teacherService.save(teacher) ? ResponseBo.ok() : ResponseBo.error();
    }

    @PutMapping
    @ApiOperation(value = "更新讲师")
    @ApiImplicitParam(name = "teacher", value = "讲师",
            dataTypeClass = Teacher.class, required = true, paramType = "body")
    public ResponseBo update(@RequestBody Teacher teacher) {
        return teacherService.update(teacher, new UpdateWrapper<Teacher>().lambda()
                .eq(Teacher::getId, teacher.getId())) ?
                ResponseBo.ok() : ResponseBo.error();
    }

    @GetMapping("/test")
    public void test() {
//        throw new CusException("测试异常", 500);
        throw new NullPointerException();
    }

}

