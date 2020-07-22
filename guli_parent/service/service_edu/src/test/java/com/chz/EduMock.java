package com.chz;

import com.chz.eduservice.controller.TeacherController;
import com.chz.statuscode.CourseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
public class EduMock {
    private MockMvc mockMvc;
    @Autowired
    private TeacherController teacherController;

    /**
     * 使用静态导包, 初始化mock
     */
    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(teacherController)
                //alwaysExpect只针对response
                .alwaysExpect(status().isOk())
                //mock返回的数据存在中文乱码, 要通过produces指定charset
                .alwaysExpect(content().contentType("application/json;charset=utf-8"))
                .alwaysDo(print())
                .build();
    }

    @Test
    @Tag("测试 /eduservice/teacher")
    public void testList() throws Exception {
        mockMvc.perform(get("/eduservice/teacher"));
    }

    @Test
    public void test() {
        System.out.println(CourseStatus.Normal);
    }


}
