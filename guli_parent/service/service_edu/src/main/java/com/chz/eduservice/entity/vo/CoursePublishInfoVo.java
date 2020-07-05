package com.chz.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/3 16:24
 * @Description: TODO
 */
@ApiModel("课程发布信息封装类")
@Data
public class CoursePublishInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;
}
