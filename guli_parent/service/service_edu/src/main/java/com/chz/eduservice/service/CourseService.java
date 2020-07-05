package com.chz.eduservice.service;

import com.chz.eduservice.entity.domain.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chz.eduservice.entity.vo.CourseInfoVo;
import com.chz.eduservice.entity.vo.CoursePublishInfoVo;

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
     * @param courseInfoVo
     */
    String addCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 按照id查询课程信息
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfoById(String courseId);

    /**
     * 根据传过来的vo对象更新对应数据
     * @return
     * @param courseInfoVo
     */
    boolean updateCourseById(CourseInfoVo courseInfoVo);

    CoursePublishInfoVo getCourseAllInfoById(String id);
}
