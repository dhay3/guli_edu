package com.chz.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chz.eduorder.entity.Order;
import com.chz.eduorder.service.OrderService;
import com.chz.response.ResponseBo;
import com.chz.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author chz
 * @since 2020-07-29
 */
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 根据课程id生成订单
     *
     * @param courseId
     * @return
     */
    @PostMapping("/{courseId}")
    public ResponseBo createOrderByCourseIdMemberId(@PathVariable String courseId, HttpServletRequest request) {
        //调用通过请求中header获取到jwt token, 根据token获取用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //返回创建的订单号
        String orderNo = orderService.createOrderByCourseIdMemberId(courseId, memberId);
        return ResponseBo.ok().data("orderNo", orderNo);
    }

    /**
     * 根据订单No查询订单信息
     *
     * @return 返回订单详细信息
     */
    @GetMapping("/{orderNo}")
    public ResponseBo getOrderInfoByOrderId(@PathVariable String orderNo) {
        Order order = orderService.getOne(new QueryWrapper<Order>().lambda()
                .eq(Order::getOrderNo, orderNo));
        return ResponseBo.ok().data("order", order);
    }
}

