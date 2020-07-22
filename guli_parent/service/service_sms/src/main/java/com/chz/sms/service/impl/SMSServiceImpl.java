package com.chz.sms.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.chz.sms.service.SMSService;
import com.chz.sms.utils.SMSUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/22 20:52
 * @Description: TODO
 */
@Service
public class SMSServiceImpl implements SMSService {
    @Autowired
    private ObjectMapper mapper;

    @Override
    public boolean sendSMS(HashMap<String, Object> param, String phoneNumber) throws JsonProcessingException {
        if (StringUtils.isEmpty(phoneNumber)) return false;
        IAcsClient client = SMSUtils.init();
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        //设置发送的相关信息,key是固定值
        request.putQueryParameter("RegionId", "cn-hangzhou");
        //电话号码
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        //对应签名
        request.putQueryParameter("SignName", "谷粒在线教育");
        //对应模板编码
        request.putQueryParameter("TemplateCode", "SMS_172887621");

        //验证码, 这里的value必须使用Json形式, 可以使用objectMapper直接将map转为Json格式
        request.putQueryParameter("TemplateParam", mapper.writeValueAsString(param));
        CommonResponse response;
        try {
            response = client.getCommonResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
        //判断短信是否发送成功
        return response.getHttpResponse().isSuccess();
    }
}
