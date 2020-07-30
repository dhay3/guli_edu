package com.chz.eduorder.feign;

import com.chz.response.ResponseBo;
import org.springframework.stereotype.Component;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/29 18:07
 * @Description: TODO
 */
@Component
public class EduCourseClientFeignImpl implements EduCourseClientFeign {
    @Override
    public ResponseBo getCourseById(String courseId) {
        return ResponseBo.error().message("远程调用失败");
    }
}
