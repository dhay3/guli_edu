package com.chz.edumember.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/24 0:25
 * @Description: TODO
 */

@Data
@ApiModel(value = "登录对象", description = "登录对象")
public class MemberLoginVo {

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;
}
