package com.chz.eduservice.entity.vo;

import com.chz.statuscode.CourseStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/3 16:24
 * @Description: TODO
 */
@ApiModel("课程发布信息封装类,用于展示course和在course list中显示")
@Data
public class CoursePublishInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("课程id")
    private String id;
    @ApiModelProperty("课程名")
    private String title;
    @ApiModelProperty("封面")
    private String cover;
    @ApiModelProperty("课时")
    private Integer lessonNum;
    @ApiModelProperty("一级分类")
    private String subjectLevelOne;
    @ApiModelProperty("二级分类")
    private String subjectLevelTwo;
    @ApiModelProperty("讲师")
    private String teacherName;
    @ApiModelProperty("价格")
    private String price;
    @ApiModelProperty("浏览数")
    private String viewCount;
    @ApiModelProperty("购买数")
    private String buyCount;
    @ApiModelProperty("状态")
    private CourseStatus status;
    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreate;
}
