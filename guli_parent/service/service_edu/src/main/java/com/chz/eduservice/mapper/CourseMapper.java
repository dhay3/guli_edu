package com.chz.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chz.eduservice.entity.domain.Course;
import com.chz.eduservice.entity.frontVo.CourseWebVo;
import com.chz.eduservice.entity.vo.CoursePublishInfoVo;
import com.chz.eduservice.entity.query.CourseQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author chz
 * @since 2020-06-24
 */
public interface CourseMapper extends BaseMapper<Course> {
    /**
     * 根据courseId查询关于course的所有信息
     *
     * @param id 这里无需标注@param
     * @return
     */
    CoursePublishInfoVo getCourseAllInfoById(@Param("id") String id);

    /**
     * 按条件分页查询course
     *
     * @param page        注意这里必须要有Page对象,否则mp无法完成分页查询
     * @param courseQuery
     * @return
     */
    List<CoursePublishInfoVo> pageCourseAllInfo(Page<CoursePublishInfoVo> page,
                                                @Param("courseQuery") CourseQuery courseQuery);

    /**
     * 根据课程id查询所有课程相关信息, 包括讲师,课程描述,一二级类别
     *
     * @param courseId
     * @return
     */
    CourseWebVo getFrontCourseDetailsByCourseId(String courseId);
}
