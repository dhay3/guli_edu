package com.chz.utils.statuscode;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/3 23:39
 * @Description: 课程状态枚举类
 */
public enum CourseStatus {
    Normal("Normal"), Draft("Draft");
    private final String val;

    CourseStatus(String val) {
        this.val = val;
    }

    private String getVal() {
        return val;
    }

    public String val() {
        return getVal();
    }
}
