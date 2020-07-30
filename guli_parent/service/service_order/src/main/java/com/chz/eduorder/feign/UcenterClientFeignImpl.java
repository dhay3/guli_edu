package com.chz.eduorder.feign;

import com.chz.response.ResponseBo;
import org.springframework.stereotype.Component;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/29 17:35
 * @Description: TODO
 */
@Component
public class UcenterClientFeignImpl implements UcenterClientFeign {

    @Override
    public ResponseBo getUserInfoByUserId(String userId) {
        return ResponseBo.error().message("远程调用失败");
    }
}
