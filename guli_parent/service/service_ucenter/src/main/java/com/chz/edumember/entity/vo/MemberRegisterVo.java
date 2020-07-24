package com.chz.edumember.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/24 0:54
 * @Description: TODO
 */
@Data
@ApiModel(value = "注册对象", description = "注册对象")
public class MemberRegisterVo {
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;
}
