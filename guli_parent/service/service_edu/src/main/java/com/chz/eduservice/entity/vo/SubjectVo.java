package com.chz.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: CHZ
 * @DateTime: 2020/6/23 16:58
 * @Description: 一级分类
 */
@Data
public class SubjectVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    //和mybatis 一对多相似
    //一个多级分类有多个二级分类
    private List<NestedSubjectVo> children = new ArrayList<>();
}
