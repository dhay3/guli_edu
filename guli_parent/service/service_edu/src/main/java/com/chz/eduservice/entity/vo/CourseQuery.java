package com.chz.eduservice.entity.vo;

import com.chz.statuscode.CourseStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/5 23:34
 * @Description: 课程条件查询封装类
 */
@ApiModel(value = "Course查询对象", description = "课程查询对象封装")
@Data
public class CourseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "一级课程分类")
    private String subjectParentId;

    @ApiModelProperty(value = "二级课程分类")
    private String subjectId;

    @ApiModelProperty(value = "课程标题")
    private String title;
    /**
     * 价格使用BigDecimal,精确度更高
     */
    @ApiModelProperty(value = "价格最小值")
    private BigDecimal beginPrice;

    @ApiModelProperty(value = "价格最大值")
    private BigDecimal endPrice;

    @ApiModelProperty(value = "课程发布状态")
    private CourseStatus status;
}