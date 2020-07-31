package com.chz.eduorder.controller;


import com.chz.eduorder.service.PayLogService;
import com.chz.response.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author chz
 * @since 2020-07-29
 */
@Slf4j
@Api("订单支付相关api")
@RestController
@RequestMapping("/eduorder/paylog")
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    /**
     * 生成微信支付二维码
     * 二维码中包含订单号
     */
    @ApiOperation("根据订单号生成二维码")
    @GetMapping("/qrcode/{orderNo}")
    public ResponseBo createQRCodeMap(@PathVariable String orderNo) {
        Map<String, Object> map = payLogService.createQRCodeMap(orderNo);
        System.out.println("createQRCodeMap\n" + map);
        return ResponseBo.ok().data(map);
    }

    /**
     * 根据订单号, 查询订单支付的状态
     */
    @ApiOperation("status 0 表示未支付, 1表示已经支付")
    @GetMapping("/{orderNo}")
    public ResponseBo getOrderStatusInWX(@PathVariable String orderNo) {
        //需要调用weixin提供的api获取信息
        Map<String, String> res = payLogService.queryOrderStatusByOrderId(orderNo);
        log.info("queryOrderStatus--->{}", res);
        if (Objects.equals(res, null)) {
            return ResponseBo.error().message("支付出错");
        } else {
            //如果微信后台显示支付成功, 就更新数据库中的数据
            if ("SUCCESS".equals(res.get("trade_status"))) {
                //添加记录到支付表中, 更新订单表
                payLogService.updateOrderStatus(res);
                return ResponseBo.ok().message("支付成功");
            }
        }
        return ResponseBo.ok().code(25000).message("支付中...");
    }
}

