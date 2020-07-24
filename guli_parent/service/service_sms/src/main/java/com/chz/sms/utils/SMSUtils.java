package com.chz.sms.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.chz.utils.AliyunUtils;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/22 23:23
 * @Description: 短信服务工具类
 */
public class SMSUtils {
    private static final String REGION_DI = "cn-hangzhou";

    public static IAcsClient init() {
        DefaultProfile profile = DefaultProfile.getProfile(REGION_DI, AliyunUtils.KEY_ID, AliyunUtils.KEY_SECRET);
        return new DefaultAcsClient(profile);

    }
}
