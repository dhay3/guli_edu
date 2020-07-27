package com.chz;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chz.eduservice.entity.domain.Teacher;
import com.chz.eduservice.entity.frontVo.CourseWebVo;
import com.chz.eduservice.entity.query.CourseQuery;
import com.chz.eduservice.service.CourseService;
import com.chz.eduservice.service.SubjectService;
import com.chz.eduservice.service.TeacherService;
import com.chz.statuscode.CourseStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/6 23:48
 * @Description: TODO
 */
@SpringBootTest
public class DBTest {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SubjectService subjectService;

    /**
     * 测试course分页条件查询
     */
    @Test
    void QueryCoursePage() {
        CourseQuery courseQuery = new CourseQuery();
//        courseQuery.setSubjectParentTitle("前端开发");
//        courseQuery.setSubjectTitle("Java");
//        courseQuery.setStatus(CourseStatus.Draft);
        courseQuery.setStatus(CourseStatus.Normal);
    }

    @Test
    void QueryTeacherPage() {
        Page<Teacher> page = teacherService.page(new Page<>(1, 3));
        System.out.println(page.getTotal());
        page.getRecords().forEach(System.out::println);
    }

    @Test
    void testSubject() {
        CourseWebVo frontCourseDetailsByCourseId = courseService.getFrontCourseDetailsByCourseId("101");
        System.out.println(frontCourseDetailsByCourseId);
    }



}
