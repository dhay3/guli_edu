package com.chz.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 自定义异常处理
 */
@ApiModel("自定义异常处理")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CusException extends RuntimeException {
    @ApiModelProperty("错误异常码")
    private Integer errorCode;

    public CusException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
