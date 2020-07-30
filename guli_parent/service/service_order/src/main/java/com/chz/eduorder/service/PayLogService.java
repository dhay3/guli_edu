package com.chz.eduorder.service;

import com.chz.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author chz
 * @since 2020-07-29
 */
public interface PayLogService extends IService<PayLog> {

    Map<String, Object> createQRCodeMap(String orderNo);

    Map<String, String> queryOrderStatusByOrderId(String orderNo);

    void updateOrderStatus(Map<String, String> res);
}
