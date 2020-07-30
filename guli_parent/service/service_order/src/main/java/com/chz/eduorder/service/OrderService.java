package com.chz.eduorder.service;

import com.chz.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author chz
 * @since 2020-07-29
 */
public interface OrderService extends IService<Order> {

    String createOrderByCourseIdMemberId(String courseId, String memberId);
}
