package com.chz.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: CHZ
 * @DateTime: 2020/6/23 11:31
 * @Description: 映射excel实体类
 */
@Data
@ApiModel("映射excel实体类")
public class SubjectData {
    @ApiModelProperty("一级分类名")
    @ExcelProperty(index = 0)//该注解可以忽略悉知
    private String oneSubjectName;
    @ApiModelProperty("二级分类名")
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
