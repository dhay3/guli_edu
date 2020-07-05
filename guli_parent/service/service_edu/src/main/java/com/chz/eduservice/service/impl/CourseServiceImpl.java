package com.chz.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.eduservice.entity.domain.Course;
import com.chz.eduservice.entity.domain.CourseDescription;
import com.chz.eduservice.entity.vo.CourseInfoVo;
import com.chz.eduservice.entity.vo.CoursePublishInfoVo;
import com.chz.eduservice.mapper.CourseDescriptionMapper;
import com.chz.eduservice.mapper.CourseMapper;
import com.chz.eduservice.service.CourseService;
import com.chz.utils.CusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author chz
 * @since 2020-06-24
 */
@Service
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;

    /**
     * 添加课程信息
     * 课程描述信息是一对一的关系,但是两个实体类的id是主键策略自动生成的
     * description的id就是course的id
     * 通过insert会将主键回显到id的特性, 为description设置主键
     *
     * @param courseInfoVo 封装课程信息表和课程描述的vo封装对象
     */
    @Override
    public String addCourseInfo(CourseInfoVo courseInfoVo) {
        //1.添加课程信息
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int executeCount = baseMapper.insert(course);
        //如果添加条数小于1,即未插入数据
        if (executeCount < 1) {
            throw new CusException("添加课程信息失败", 20001);
        }
        //2.添加课程描述
        CourseDescription courseDescription = new CourseDescription();
        //mp可以通过course.getId()可以直接拿到插入的id值
        //如果设置了id,那么UUID主键策略将不会成效,但是如果是主键自增策略实现,那么主键自增策略优先
        courseDescription.setId(course.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        //创建时间和修改时间会自动插入,无需关注
        courseDescriptionMapper.insert(courseDescription);
        return course.getId();
    }

    /**
     * 根据课程id查询对应的信息封装到vo中
     * 由于course与description是一对一的,且description的id和course的id相同
     * 两者都可以通过selectById查询
     *
     * @param courseId 课程id
     * @return 返回vo对象
     */
    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {
        Course course = baseMapper.selectById(courseId);
        log.info("course:{}", course);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoVo);
        //右description的id就是课程的id是同一个所有可以通过byId查询
        CourseDescription courseDescription = courseDescriptionMapper.selectById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    /**
     * 修改course信息和courseDescription
     *
     * @param courseInfoVo
     * @return
     */
    @Override
    public boolean updateCourseById(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        CourseDescription courseDescription = new CourseDescription();
        //由于courseInfoVo中有id字段,所以也会封装进courseDescription中
        BeanUtils.copyProperties(courseInfoVo, courseDescription);
        return baseMapper.updateById(course) > 0 && courseDescriptionMapper.updateById(courseDescription) > 0;
    }

    /**
     *
     * @param id 根据courseId查询所用的course信息
     * @return
     */
    @Override
    public CoursePublishInfoVo getCourseAllInfoById(String id) {
        return baseMapper.getCourseAllInfoById(id);
    }
}
