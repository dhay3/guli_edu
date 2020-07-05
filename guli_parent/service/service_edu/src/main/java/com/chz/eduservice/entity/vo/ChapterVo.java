package com.chz.eduservice.entity.vo;

/**
 * @Author: CHZ
 * @DateTime: 2020/6/25 21:06
 * @Description: 章节对应vo
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel("章节封装vo")
public class ChapterVo {
    @ApiModelProperty("章节id")
    private String id;
    @ApiModelProperty("章节标题")
    private String title;
    @ApiModelProperty(value = "章节对应的视频", notes = "章节对应视频一对多")
    private List<VideoVo> children = new ArrayList<>();
}
