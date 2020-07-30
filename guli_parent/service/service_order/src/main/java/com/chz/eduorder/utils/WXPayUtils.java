package com.chz.eduorder.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/30 17:33
 * @Description: TODO
 */
@Data
@Component
@ConfigurationProperties(prefix = "weixin.pay")
@PropertySource("classpath:wxpay.properties")
public class WXPayUtils {
    @ApiModelProperty("关联的公众号appid")
    private String appid;
    @ApiModelProperty("商户号")
    private String partner;
    @ApiModelProperty("商户key")
    private String partnerkey;
    @ApiModelProperty("回调地址")
    private String notifyurl;

    public static String APP_ID;
    public static String PARTNER_KEY;
    public static String NOTIFY_URL;
    public static String PARTNER;

    @PostConstruct
    public void init() {
        APP_ID = appid;
        PARTNER = partner;
        PARTNER_KEY = partnerkey;
        NOTIFY_URL = notifyurl;
    }

}
