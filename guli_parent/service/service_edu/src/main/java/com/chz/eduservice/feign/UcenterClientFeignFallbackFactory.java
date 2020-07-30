package com.chz.eduservice.feign;

import com.chz.response.ResponseBo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/28 17:08
 * @Description: TODO
 */
@Component
public class UcenterClientFeignFallbackFactory implements FallbackFactory<UcenterClientFeign> {
    @Override
    public UcenterClientFeign create(Throwable cause) {
        return new UcenterClientFeign() {
            @Override
            public ResponseBo getUserInfoByToken(HttpServletRequest request) {
                return ResponseBo.error().message("远程调用token获取失败");
            }

            @Override
            public ResponseBo getUserInfoByUserId(String userId) {
                return ResponseBo.error().message("运程获取用户信息失败");
            }

        };
    }
}
