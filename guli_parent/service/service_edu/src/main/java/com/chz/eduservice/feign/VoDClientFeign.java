package com.chz.eduservice.feign;

import com.chz.utils.ResponseBo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/18 20:32
 * @Description: @FeignClient 指明调用服务名, fallbackFactory如果方法请求失败调用指定的处理类
 */
@Component
@FeignClient(value = "service-vod",//对应application name
        fallback = VoDClientFeignImpl.class)//当服务不可用时, 触发服务降级与熔断
public interface VoDClientFeign {
    /**
     * 对应的url必须是全路径
     * 注意这里pathVariable必须指定名称
     *
     * @param videoSourceId
     * @return
     */
    @DeleteMapping(path = "/eduvod/video/{videoSourceId}")
    ResponseBo deleteVoDByVideoSourceId(@PathVariable("videoSourceId") String videoSourceId);

    /**
     * 注意feign模块下, 所有的参数多要通过@RequestParm或@RequestBody或@PathVariable来指定
     * 且@RequestBody有且只有一个
     *
     * @param videoSourceIds
     * @return
     */
    @DeleteMapping(path = "/eduvod/video/deleteBatch")
    ResponseBo deleteBatchVoDByVideoSourceId(@RequestParam("videoSourceIds") List<String> videoSourceIds);
}
