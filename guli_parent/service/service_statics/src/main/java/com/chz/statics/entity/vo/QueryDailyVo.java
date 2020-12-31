package com.chz.statics.entity.vo;

import com.chz.statics.entity.TypeEnum;
import lombok.Data;

/**
 * @Author: CHZ
 * @DateTime: 2020/8/2 18:02
 * @Description: TODO
 */
@Data
public class QueryDailyVo {
    private TypeEnum type;
    private String end;
    private String begin;
}
