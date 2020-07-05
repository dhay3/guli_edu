package com.chz.eduservice.mapper;

import com.chz.eduservice.entity.domain.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chz.eduservice.entity.vo.CoursePublishInfoVo;
import org.apache.ibatis.annotations.Param;

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
}
