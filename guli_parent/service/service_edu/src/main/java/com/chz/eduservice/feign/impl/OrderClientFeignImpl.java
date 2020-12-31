package com.chz.eduservice.feign.impl;

import com.chz.eduservice.feign.OrderClientFeign;
import com.chz.response.ResponseBo;
import org.springframework.stereotype.Component;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/31 15:22
 * @Description: TODO
 */
@Component
public class OrderClientFeignImpl implements OrderClientFeign {
    @Override
    public ResponseBo getOrderStatusInDB(String courseId, String memberId) {
        return ResponseBo.error().message("获取订单状态失败");
    }
}
