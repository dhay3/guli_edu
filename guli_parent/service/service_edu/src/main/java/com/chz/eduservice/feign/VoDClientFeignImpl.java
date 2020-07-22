package com.chz.eduservice.feign;

import com.chz.response.ResponseBo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/20 14:03
 * @Description: TODO
 */
@Component//需要注入到ioc中
public class VoDClientFeignImpl implements VoDClientFeign {
    @Override
    public ResponseBo deleteVoDByVideoSourceId(String videoSourceId) {
        return ResponseBo.error().message("删除视频失败");
    }

    @Override
    public ResponseBo deleteBatchVoDByVideoSourceId(List<String> videoSourceIds) {
        return ResponseBo.error().message("删除视频失败");
    }
}
