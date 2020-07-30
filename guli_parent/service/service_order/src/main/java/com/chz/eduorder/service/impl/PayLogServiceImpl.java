package com.chz.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.eduorder.entity.Order;
import com.chz.eduorder.entity.PayLog;
import com.chz.eduorder.mapper.PayLogMapper;
import com.chz.eduorder.service.OrderService;
import com.chz.eduorder.service.PayLogService;
import com.chz.eduorder.utils.HttpClient;
import com.chz.eduorder.utils.WXPayUtils;
import com.chz.exception.CusException;
import com.chz.statuscode.ResultCode;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author chz
 * @since 2020-07-29
 */
@Slf4j
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Autowired
    private OrderService orderService;

    /**
     * 根据订单号生成二维码
     *
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, Object> createQRCodeMap(String orderNo) {
        //1.根据订单号查询订单信息
        Order order = orderService.getOne(new QueryWrapper<Order>().lambda()
                .eq(Order::getOrderNo, orderNo));
        //2.使用map设置生成二维码需要参数
        return parseXmlStringToMap(order);
    }

    /**
     * 调用微信api, 查询订单支付状态
     *
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, String> queryOrderStatusByOrderId(String orderNo) {
        try {
            //1、封装参数
            Map<String, String> m = new HashMap<>();
            m.put("appid", WXPayUtils.APP_ID);
            m.put("mch_id", WXPayUtils.PARTNER);
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, WXPayUtils.PARTNER_KEY));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            //6、转成Map
            return WXPayUtil.xmlToMap(xml);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CusException();
        }
    }

    /**
     * 在
     *
     * @param map
     */
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //获取订单号
        String orderNo = map.get("out_trade_no");
        Order order = orderService.getOne(new QueryWrapper<Order>().lambda()
                .eq(Order::getOrderNo, orderNo));
        //更新订单状态
        if (order.getStatus() == 1) return;
        //1表示已经支付
        order.setStatus(1);
        //更新订单表
        orderService.updateById(order);
        PayLog payLog = new PayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        //在订单记录中插入信息
        baseMapper.insert(payLog);//插入到支付日志表
    }


    /**
     * 将参数转为xml, 发送请求, 请求返回的是xml字符串,将字符串转为map
     * 从转换的map中获取值,封装成一个map
     *
     * @param order
     * @return
     */
    private Map<String, Object> parseXmlStringToMap(Order order) {
        Map<String, String> params = setParams(order);
        System.out.println("params--->"+params);
        //发送http请求, 传递xml格式参数, 微信提供固定的接口
        HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        try {
            //设置xml格式的参数的字符串
            httpClient.setXmlParam(WXPayUtil
                    .generateSignedXml(params, WXPayUtils.PARTNER_KEY));
            //请求协议位https
            httpClient.setHttps(true);
            //post发送请求
            httpClient.post();
            //获取发送请求返回的结果, 这里返回的是带有xml标签形式的字符串
            String content = httpClient.getContent();
            //将xml字符串转为Map
            Map<String, String> tmp = WXPayUtil.xmlToMap(content);
            log.warn("tmp--->{}",tmp);
            //从xml中获取最终封装的结果
            Map<String, Object> res = getResultMap(order, tmp);
            log.warn("createMap--->{}", res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CusException("生成二维码失败", ResultCode.ERROR.val());
        }
    }

    /**
     * 设置发送请求的参数
     * 详情参考 https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_5
     *
     * @return
     */
    private Map<String, String> setParams(Order order) {
        HashMap<String, String> params = new HashMap<>();
        //map的key是固定值
        params.put("appid", WXPayUtils.APP_ID);
        params.put("mch_id", WXPayUtils.PARTNER);
        //生成随机字符串, 防止伪造
        params.put("nonce_str", WXPayUtil.generateNonceStr());
        params.put("body", order.getCourseTitle());
        //二维码唯一标识
        params.put("out_trade_no", order.getOrderNo());
        //BigDecimal运算时使用内置的方法, 微信规定单位为分, 需要将BigDecimal转为int形式
        params.put("total_fee", order.getTotalFee()
                .multiply(new BigDecimal(100)).intValue()+"");
        //中断地址
        params.put("spbill_create_ip", "127.0.0.1");
        params.put("notify_url", WXPayUtils.NOTIFY_URL);
        params.put("trade_type", "NATIVE");
        return params;
    }

    /**
     * 截取map,获取最终返回的map
     *
     * @param order
     * @param map
     * @return
     */
    private Map<String, Object> getResultMap(Order order, Map<String, String> map) {
        Map<String, Object> res = new HashMap<>();
        res.put("out_trade_no", order.getOrderNo());
        res.put("course_id", order.getCourseId());
        res.put("total_fee", order.getTotalFee());
        res.put("result_code", map.get("result_code"));
        res.put("code_url", map.get("code_url"));
        return res;
    }
}
