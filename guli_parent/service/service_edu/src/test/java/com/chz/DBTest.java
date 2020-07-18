package com.chz;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chz.eduservice.entity.domain.Teacher;
import com.chz.eduservice.entity.vo.CourseQuery;
import com.chz.eduservice.service.CourseService;
import com.chz.eduservice.service.SubjectService;
import com.chz.eduservice.service.TeacherService;
import com.chz.utils.statuscode.CourseStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

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
    void testSubject(){
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        Arrays.stream(integers.toArray()).forEach(System.out::println);
    }

}
