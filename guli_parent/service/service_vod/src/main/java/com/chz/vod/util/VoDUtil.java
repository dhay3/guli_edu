package com.chz.vod.util;

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/17 21:34
 * @Description: TODO
 */
@Component
@PropertySource("classpath:vod-config.properties")
//public class VoDUtil implements InitializingBean {
public class VoDUtil {
    @Value("${aliyun.vod.file.keyid}")
    private String accessKey;
    @Value("${aliyun.vod.file.keysecret}")
    private String accessKeySecret;

    public static String ACCESS_KEY;
    public static String ACCESS_KEY_SECRET;

    @PostConstruct
    private void init() {
        ACCESS_KEY = accessKey;
        ACCESS_KEY_SECRET = accessKeySecret;
    }

    /**
     * 初始化vod方法
     *
     * @return
     * @throws ClientException
     */
    public static DefaultAcsClient initVodClient() throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, ACCESS_KEY, ACCESS_KEY_SECRET);
        return new DefaultAcsClient(profile);
    }
}
