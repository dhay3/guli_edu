package com.chz.eduservice.entity.vo;

/**
 * @Author: CHZ
 * @DateTime: 2020/6/25 21:06
 * @Description: 视频对应vo
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("视频封装vo")
public class VideoVo {
    @ApiModelProperty("视频id")
    private String id;
    @ApiModelProperty("视频标题")
    private String title;
}
