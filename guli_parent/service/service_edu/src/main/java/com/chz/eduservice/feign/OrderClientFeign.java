package com.chz.eduservice.feign;

import com.chz.eduservice.feign.impl.OrderClientFeignImpl;
import com.chz.response.ResponseBo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/31 15:21
 * @Description: TODO
 */
@Component
@FeignClient(value = "service-order",
        fallback = OrderClientFeignImpl.class)
public interface OrderClientFeign {
    @GetMapping("/eduorder/order/check/{courseId}/{memberId}")
    ResponseBo getOrderStatusInDB(@PathVariable("courseId") String courseId,
                                  @PathVariable("memberId") String memberId);

}
