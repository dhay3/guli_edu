package com.chz.edumember.controller;

import com.chz.edumember.entity.Member;
import com.chz.edumember.service.MemberService;
import com.chz.edumember.utils.HttpClientUtils;
import com.chz.edumember.utils.WechatUtils;
import com.chz.exception.CusException;
import com.chz.statuscode.ResultCode;
import com.chz.utils.JwtUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/24 21:18
 * @Description: OAuth流程:
 * 用户请求登入跳转到微信, 确认登入, 微信将临时票据code返回给第三方,第三方通过callback将code换取
 * access_token, 通过access_token通过认证服务,获取资源信息
 */
@Slf4j
@Controller
@RequestMapping("/api/ucenter/wx")
public class WXAPIController {
    @Autowired
    private MemberService memberService;

    @ApiOperation("跳转到微信扫描二维码")
    @GetMapping("/login")
    public String redirectWXQRCode() {
//        方法一字符拼接, 不推荐
//        return "redirect:https://open.weixin.qq.com/connect/qrconnect?" +
//                "appid=" + WechatUtils.WX_APP_ID+
//                "&redirect_uri=" +WechatUtils.WX_REDIRECT_URL+
//                "&response_type=code" +
//                "&scope=snsapi_login#wechat_redirect ";

        //方法二, 使用format函数
        String redirectUrl = WechatUtils.WX_REDIRECT_URL;
        String state = UUID.randomUUID().toString();
        try {
            //对重定向的地址编码,为了防止url中有中文乱码
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new CusException("重定向地址错误", ResultCode.ERROR.val());
        }
        //注意检查url左右是否右空格, 如果有空格路径请求将出错
        String baseUrlPattern = "https://open.weixin.qq.com/connect/qrconnect?" +
                "appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        String url = String.format(baseUrlPattern, WechatUtils.WX_APP_ID, redirectUrl, state);
        System.out.println(url);
        //跳转到指定url交给微信处理, 微信处理完后回调指定redirect_url,带上code
        //第三方应用通过code换取access-token
        return "redirect:" + url;
    }

    @ApiOperation("扫描微信二维码后对应跳转的api接口")
    @GetMapping("/callback")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "接收用于认证的code"),
            @ApiImplicitParam(name = "state", value = "防止伪造攻击的标识码,可缺")
    })
    public String callback(@RequestParam(required = true) String code, String state) {
        //获取access_token
        String accessTokenInfo = getAccessTokenByCode(code);
        /*
        用code访问返回的响应体中包含
        access_token,expires_in,refresh_token,openid,scope,unionid
         */
        //将响应体转换为map对象, 可以将json转为map
        Map<String, Object> accessInfoMap = WechatUtils.castJsonInfoToMap(accessTokenInfo);
        //获取信息
        String access_token = accessInfoMap.get("access_token").toString();
        String openid = accessInfoMap.get("openid").toString();
        Member member = memberService.saveWechatUserIfNotExist(openid, access_token);
        //返回首页面
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return "redirect:http://localhost:3000?token=" + token;
    }

    private String getAccessTokenByCode(String code){
        String accessTokenUrl = WechatUtils.getAccessTokenUrlByCode(code);
        //使用httpclient发送请求获取响应体中的内容,自定义工具类
        String accessTokenInfo = null;
        try {
            accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CusException("换取access_token失败", ResultCode.ERROR.val());
        }
        return accessTokenInfo;
    }
}
