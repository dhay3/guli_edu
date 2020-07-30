package com.chz.eduorder.feign;

import com.chz.response.ResponseBo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/28 17:06
 * @Description: TODO
 */
@Component
@FeignClient(value = "service-ucenter",
        fallback = UcenterClientFeignImpl.class)
public interface UcenterClientFeign {
    /**
     * feign组件中必须指定@ReqeustParam或@PathVariable参数名
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/api/ucenter/{userId}")
    ResponseBo getUserInfoByUserId(@PathVariable("userId") String userId);
}
