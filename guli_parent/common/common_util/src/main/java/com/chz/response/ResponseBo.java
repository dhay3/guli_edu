package com.chz.response;

import com.chz.statuscode.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回统一状态格式
 */
@Data
//最好不要用lombok的fluent, 因为spring注入值是通过setXxx,lombok修改了getter和setter,会导致值无法注入
// @Accessors(chain = true)
@ApiModel("统一状态码格式")
public class ResponseBo {
    @ApiModelProperty("是否成功")
    private Boolean success;
    @ApiModelProperty("返回状态码")
    private Integer code;
    @ApiModelProperty("返回消息")
    private String message;
    //通过map将值传到前端
    @ApiModelProperty("返回数据")
    private Map<String, Object> data = new HashMap<>();

    private ResponseBo() {
    }

    /**
     * 静态工厂
     * 成功
     */
    public static ResponseBo ok() {
        return new ResponseBo()
                .success(true)
                .code(ResultCode.SUCCESS.val())
                .message("请求成功");
    }

    /**
     * 静态工程
     * 失败
     */
    public static ResponseBo error() {
        return new ResponseBo()
                .success(false)
                .code(ResultCode.ERROR.val())
                .message("请求失败");
    }

    /**
     * 链式编程/流式编程
     *
     * @return 静态方法获取的实例对象
     */
    public ResponseBo success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public ResponseBo message(String message) {
        this.setMessage(message);
        return this;
    }

    public ResponseBo code(Integer code) {
        this.setCode(code);
        return this;
    }

    public ResponseBo data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public ResponseBo data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
