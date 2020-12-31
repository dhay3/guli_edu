package com.chz.statics.feign;

import com.chz.response.ResponseBo;
import com.chz.statics.feign.impl.UcenterFeignClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: CHZ
 * @DateTime: 2020/8/1 16:05
 * @Description: TODO
 */
@Component
@FeignClient(value = "service-ucenter", fallback = UcenterFeignClientImpl.class)
public interface UcenterFeignClient {
    @GetMapping("/api/ucenter/date/{date}")
    ResponseBo queryRegisterCountByDate(@PathVariable("date") String date);
}
