package com.chz.statics.controller;


import com.chz.response.ResponseBo;
import com.chz.statics.entity.vo.QueryDailyVo;
import com.chz.statics.service.DailyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author chz
 * @since 2020-08-01
 */
@RestController
@RequestMapping("/statics/daily")
public class DailyController {
    @Autowired
    private DailyService dailyService;

    /**
     * 统计同一天注册的人数, 如果提前调用接口就会产生错误
     * 不采用先查询数据库然后再判断是添加还是更新, 会访问3次数据库
     * 采用先删除让后再添加数据库, 访问2次
     *
     * @param date
     * @return
     */
    @ApiOperation(value = "统计一天注册的人数")
    @GetMapping("/{date}")
    public ResponseBo statisticsRegisterCountByDate(@PathVariable String date) {
        dailyService.statisticsRegisterCountByDate(date);
        return ResponseBo.ok();
    }

    /**
     * 图表显示
     * 返回日期数组和具体数据的数组
     * type为枚举类型, 需要让前端的value与枚举的字面量相同
     */
    @ApiOperation("根据给定的日期和查询类型查询数据库")
    //注意使用RequestBody需要使用post请求,因为get没有请求体
    @PostMapping("/showChart")
    public ResponseBo showChartByCondition(@RequestBody QueryDailyVo queryDailyVo) {
        System.out.println(queryDailyVo.getType());
        Map<String, Object> data = dailyService.showChartByCondition(queryDailyVo);
        return ResponseBo.ok().data("data", data);
    }
}

