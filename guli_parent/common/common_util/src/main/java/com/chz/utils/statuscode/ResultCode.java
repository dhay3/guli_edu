package com.chz.utils.statuscode;

import io.swagger.annotations.ApiModel;

/**
 * 统一返回结果
 */
@ApiModel("结果状态码")
public enum ResultCode {
    //对应vue-admin-template
    SUCCESS(20000),
    ERROR(20001);
    private final int value;

    ResultCode(int value) {
        this.value = value;
    }

    private int getValue() {
        return value;
    }

    public int val() {
        return getValue();
    }
}
