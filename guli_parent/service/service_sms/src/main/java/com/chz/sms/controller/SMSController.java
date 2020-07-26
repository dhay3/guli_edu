package com.chz.sms.controller;

import com.chz.response.ResponseBo;
import com.chz.sms.service.SMSService;
import com.chz.utils.RandomUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/22 20:51
 * @Description: TODO
 */
@RestController
@RequestMapping("/edusms/sms")
@CrossOrigin
public class SMSController {
    @Autowired
    private SMSService smsService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/{phoneNumber}")
    public ResponseBo sendSMS(@PathVariable String phoneNumber) throws JsonProcessingException {
        //从redis中获取验证码, 如果能直接获取到就直接返回
        Object codeInRedis = redisTemplate.opsForValue().get(phoneNumber);
        if (!ObjectUtils.isEmpty(codeInRedis)) {
            return ResponseBo.ok().data("code", codeInRedis);
        }
        //如果redis中没有生成随机验证码, 传给aliyun进行短信发送服务
        String code = RandomUtil.getFourBitRandom();
        HashMap<String, Object> param = new HashMap<>();
        param.put("code", code);
        //调用service发送短信
        boolean sent = smsService.sendSMS(param, phoneNumber);
        //如果调用成功就将数据放入redis中
        if (sent) {
            //设置当前key的ttl为5分钟
            redisTemplate.opsForValue().set(phoneNumber, code, 40, TimeUnit.MINUTES);
            return ResponseBo.ok().data("code", code);
        } else {
            return ResponseBo.error().message("短信发送失败");
        }
    }
}
