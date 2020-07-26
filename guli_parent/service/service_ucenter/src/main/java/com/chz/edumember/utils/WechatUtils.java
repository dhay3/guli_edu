package com.chz.edumember.utils;

import com.google.gson.Gson;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/24 21:07
 * @Description: TODO
 */
@Data
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
    /**
     * 根据票据换取access_token
     *
     * @param code
     * @return
     */
    public static String getAccessTokenUrlByCode(String code) {
        //根据一次性的code(授权码)访问固定地址,获取access_token(令牌)和oppen_id
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        return String.format(baseAccessTokenUrl, WechatUtils.WX_APP_ID, WechatUtils.WX_APP_SECRET, code);
    }

    /**
     * 根据access_token 获取用户信息
     *
     * @param access_token
     * @return
     */
    public static String getUserInfoUrlByAccessToken(String access_token) {
        //根据令牌访问认证服务器和资源服务器
        String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s" +
                "&openid=%s";
        return String.format(baseUserInfoUrl, access_token, WechatUtils.WX_APP_ID);
    }

    /**
     * 将Json转为Map对象
     *
     * @param Info
     * @return
     */
    public static Map<String, Object> castJsonInfoToMap(String Info) {
        return new Gson().fromJson(Info, Map.class);
    }

}
