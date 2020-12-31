package com.chz.statics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chz.statics.entity.Daily;
import com.chz.statics.entity.vo.QueryDailyVo;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author chz
 * @since 2020-08-01
 */
public interface DailyService extends IService<Daily> {

    int statisticsRegisterCountByDate(String date);

    Map<String,Object> showChartByCondition(QueryDailyVo queryDailyVo);
}
