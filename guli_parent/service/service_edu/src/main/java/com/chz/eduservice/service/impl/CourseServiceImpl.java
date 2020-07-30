package com.chz.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.eduservice.entity.domain.Course;
import com.chz.eduservice.entity.domain.CourseDescription;
import com.chz.eduservice.entity.domain.Teacher;
import com.chz.eduservice.entity.frontVo.CourseWebVo;
import com.chz.eduservice.entity.frontVo.FrontCourseQueryVo;
import com.chz.eduservice.entity.vo.CourseInfoVo;
import com.chz.eduservice.entity.vo.CoursePublishInfoVo;
import com.chz.eduservice.entity.query.CourseQuery;
import com.chz.eduservice.mapper.CourseDescriptionMapper;
import com.chz.eduservice.mapper.CourseMapper;
import com.chz.eduservice.service.*;
import com.chz.exception.CusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    /**
     * 注意为了解耦, 调用其他类的方法是要用service层而不是mapper层
     */
    @Autowired
    private CourseDescriptionMapper courseDescriptionService;//这里要调用service层悉知

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private TeacherService teacherService;

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
        courseDescriptionService.insert(courseDescription);
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
        Teacher teacher = teacherService.getById(course.getTeacherId());
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        courseInfoVo.setTeacherName(teacher.getName());
        BeanUtils.copyProperties(course, courseInfoVo);
        //右description的id就是课程的id是同一个所有可以通过byId查询
        CourseDescription courseDescription = courseDescriptionService.selectById(courseId);
        //有可能存在数据库中的描述信息为null, 需要判断
        courseInfoVo.setDescription(courseDescription == null ? null : courseDescription.getDescription());
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
        return baseMapper.updateById(course) > 0 && courseDescriptionService.updateById(courseDescription) > 0;
    }

    /**
     * @param id 根据courseId查询所用的course信息
     * @return
     */
    @Override
    public CoursePublishInfoVo getCourseAllInfoById(String id) {
        return baseMapper.getCourseAllInfoById(id);
    }

    /**
     * 分页查询course
     *
     * @param course
     * @param cur
     * @param size
     * @return
     */
    @Override
    public Page<CoursePublishInfoVo> pageCourseAllInfo(Integer cur, Integer size, CourseQuery courseQuery) {
        Page<CoursePublishInfoVo> page = new Page<>(cur, size);
        //想象不同page方法也是需要传入一个page作为参数
        //将带条件的查询结果封装到page中,作为page中的数据,查询出来的一条结果封装到CoursePublishInfoVo中
        page.setRecords(baseMapper.pageCourseAllInfo(page, courseQuery));
        return page;
    }

    /**
     * 需要级联删除, 所以不能使用mybatis Plus自带的方法
     *
     * @param courseId
     * @return
     */
    @Override
    public boolean removeCourseById(String courseId) {
        //这里其他类使用service层, 本业务类使用mapper层
        //根据courseId删除课程小节
        videoService.deleteByCourseId(courseId);
        //根据课程id删除章节
        chapterService.deleteChapterByCourseId(courseId);
        //根据课程id删除描述
        courseDescriptionService.deleteById(courseId);
        //根据课程id删除本身, 逻辑删除
        baseMapper.deleteById(courseId);
        return true;
    }

    /**
     * 封装方法
     *
     * @param target
     * @param <T>
     * @return
     */
//    private <T> LambdaQueryWrapper<T> getWrapper(Class<T> target) {
//        return new QueryWrapper<T>().lambda();
//    }

    /**
     * 查询观看书排名前八的课程
     *
     * @return
     */
    public List<Course> getTopCoursesDESC() {
        List<Course> courses = baseMapper.selectList(new QueryWrapper<Course>().lambda()
                .orderByDesc(Course::getViewCount).last("limit 8"));
        return courses;
    }

    /**
     * 返回到前端的分页信息
     *
     * @param coursePage
     * @param frontCourseQueryVo
     * @return
     */
    @Override
    public Map<String, Object> getFrontCoursesByCondition(Page<Course> coursePage, FrontCourseQueryVo frontCourseQueryVo) {
        LambdaQueryWrapper<Course> wrapper = new QueryWrapper<Course>().lambda();
        String subjectId = frontCourseQueryVo.getSubjectId();
        String subjectParentId = frontCourseQueryVo.getSubjectParentId();
        String gmtCreateSort = frontCourseQueryVo.getGmtCreateSort();
        String priceSort = frontCourseQueryVo.getPriceSort();
        String buyCountSort = frontCourseQueryVo.getBuyCountSort();
        //一级分类
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq(Course::getSubjectParentId, subjectParentId);
        }
        //二级分类
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq(Course::getSubjectId, subjectId);
        }
        //关注度(销量), 让前端传过来一个值, 根据值来判断是否排序
        if (!StringUtils.isEmpty(buyCountSort)) {
            wrapper.orderByDesc(Course::getBuyCount);
        }
        if (!StringUtils.isEmpty(gmtCreateSort)) {
            wrapper.orderByDesc(Course::getGmtCreate);
        }
        if (!StringUtils.isEmpty(priceSort)) {
            wrapper.orderByDesc(Course::getPrice);
        }
        //按照条件分页查询,将查询的结果放入到page中
        baseMapper.selectPage(coursePage, wrapper);
        HashMap<String, Object> params = new HashMap<>();
        params.put("total", coursePage.getTotal());
        params.put("records", coursePage.getRecords());
        params.put("size", coursePage.getSize());
        params.put("pages", coursePage.getPages());
        params.put("current", coursePage.getCurrent());
        params.put("next", coursePage.hasNext());
        params.put("pre", coursePage.hasPrevious());
        return params;
    }

    /**
     * 根据课程id查询所有课程相关信息, 包括讲师,课程描述,一二级类别
     *
     * @param courseId
     * @return
     */
    @Override
    public CourseWebVo getFrontCourseDetailsByCourseId(String courseId) {
        return baseMapper.getFrontCourseDetailsByCourseId(courseId);
    }
}
