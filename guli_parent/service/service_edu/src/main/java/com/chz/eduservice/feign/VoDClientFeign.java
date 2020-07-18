package com.chz.eduservice.feign;

import com.chz.utils.ResponseBo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/18 20:32
 * @Description: @FeignClient 指明调用服务名, fallbackFactory如果方法请求失败调用指定的处理类
 */
@Component
@FeignClient("service-vod")//对应application name
public interface VoDClientFeign {
    /**
     * 注意这里pathVariable必须指定名称
     * @param videoId
     * @return
     */
    @DeleteMapping(path = "/eduvod/video/{videoId}")
    public ResponseBo deleteVoDByVideoId(@PathVariable("videoId") String videoId);
}
