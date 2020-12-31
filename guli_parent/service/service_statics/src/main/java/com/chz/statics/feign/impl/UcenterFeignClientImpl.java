package com.chz.statics.feign.impl;

import com.chz.response.ResponseBo;
import com.chz.statics.feign.UcenterFeignClient;
import org.springframework.stereotype.Component;

/**
 * @Author: CHZ
 * @DateTime: 2020/8/1 16:08
 * @Description: TODO
 */
@Component
public class UcenterFeignClientImpl implements UcenterFeignClient {
    @Override
    public ResponseBo queryRegisterCountByDate(String date) {
        return ResponseBo.error().message("获取注册人数错误");
    }
}
