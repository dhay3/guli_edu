package com.chz.edumember.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/24 21:07
 * @Description: TODO
 */
@Component
@ConfigurationProperties(prefix = "wx.open")
@PropertySource("classpath:oauth.properties")
public class WechatUtils {
    private String appId;
    private String appSecret;
    private String redirectUrl;

    public static String WX_APP_ID;
    public static String WX_APP_SECRET;
    public static String WX_REDIRECT_URL;

    @PostConstruct
    public void init() {
        WX_APP_ID = appId;
        WX_APP_SECRET = appSecret;
        WX_REDIRECT_URL = redirectUrl;
    }
}
