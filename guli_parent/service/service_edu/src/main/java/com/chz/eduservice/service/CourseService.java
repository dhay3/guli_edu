package com.chz.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chz.eduservice.entity.domain.Course;
import com.chz.eduservice.entity.vo.CourseInfoVo;
import com.chz.eduservice.entity.vo.CoursePublishInfoVo;
import com.chz.eduservice.entity.vo.CourseQuery;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author chz
 * @since 2020-06-24
 */
public interface CourseService extends IService<Course> {

    /**
     * 添加课程
     *
     * @param courseInfoVo
     */
    String addCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 按照id查询课程信息
     *
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfoById(String courseId);

    /**
     * 根据传过来的vo对象更新对应数据
     *
     * @param courseInfoVo
     * @return
     */
    boolean updateCourseById(CourseInfoVo courseInfoVo);

    CoursePublishInfoVo getCourseAllInfoById(String id);

    /**
     * 分页查询对像,这里应该返回IPage封装对象
     *
     * @param curPage
     * @param pageSize
     * @param courseQuery
     * @return
     */
    Page<CoursePublishInfoVo> pageCourseAllInfo(Integer curPage, Integer pageSize, CourseQuery courseQuery);
}
