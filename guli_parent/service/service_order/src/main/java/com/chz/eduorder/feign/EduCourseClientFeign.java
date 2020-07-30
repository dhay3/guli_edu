package com.chz.eduorder.feign;

import com.chz.response.ResponseBo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/29 18:04
 * @Description: TODO
 */
@Component
@FeignClient(value = "service-edu", fallback = EduCourseClientFeignImpl.class)
public interface EduCourseClientFeign {
    /**
     * 根据课程id获取课程信息
     *
     * @param courseId
     * @return
     */
    @GetMapping("/eduservice/course/{id}")
    ResponseBo getCourseById(@PathVariable("id") String courseId);

}
