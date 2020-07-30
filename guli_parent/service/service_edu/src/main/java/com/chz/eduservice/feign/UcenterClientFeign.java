package com.chz.eduservice.feign;

import com.chz.response.ResponseBo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/28 17:06
 * @Description: TODO
 */
@Component
@FeignClient(value = "service-ucenter",
        fallbackFactory = UcenterClientFeignFallbackFactory.class)
public interface UcenterClientFeign {
    /**
     * 使用feign组件时要使用全路径
     *
     * @param request
     * @return
     */
    @GetMapping("/api/ucenter/token")
    ResponseBo getUserInfoByToken(HttpServletRequest request);

    /**
     * feign组件中必须指定@ReqeustParam或@PathVariable参数名
     *
     * @param userId
     * @return
     */
    @GetMapping("/api/ucenter/{userId}")
    ResponseBo getUserInfoByUserId(@PathVariable("userId") String userId);
}
