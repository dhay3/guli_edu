package com.chz.statics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.statics.entity.Daily;
import com.chz.statics.entity.vo.QueryDailyVo;
import com.chz.statics.feign.UcenterFeignClient;
import com.chz.statics.mapper.DailyMapper;
import com.chz.statics.service.DailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author chz
 * @since 2020-08-01
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {
    @Autowired
    private UcenterFeignClient ucenterFeignClient;

    /**
     * 添加之前要先删除数据库中存在的值, 否则会重复添加记录
     *
     * @param date
     * @return
     */
    @Override
    public int statisticsRegisterCountByDate(String date) {
        //添加前先删除记录
        baseMapper.delete(new QueryWrapper<Daily>().lambda()
                .eq(Daily::getDateCalculated, date));
        //运程调用获取注册人数
        Integer registerCount = (Integer) ucenterFeignClient.queryRegisterCountByDate(date)
                .getData().get("registerCount");
        System.out.println(registerCount);
        Daily daily = new Daily();
        daily.setRegisterNum(registerCount)
                .setDateCalculated(date)
                .setVideoViewNum(RandomUtils.nextInt())
                .setLoginNum(RandomUtils.nextInt())
                .setCourseNum(RandomUtils.nextInt());
        return baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> showChartByCondition(QueryDailyVo queryDailyVo) {
        HashMap<String, Object> map = new HashMap<>();
        LambdaQueryWrapper<Daily> wrapper = new QueryWrapper<Daily>().lambda()
                .between(Daily::getDateCalculated, queryDailyVo.getBegin(), queryDailyVo.getEnd())
                .orderByAsc(Daily::getDateCalculated);
        List<Daily> dailies = baseMapper.selectList(wrapper);
        System.out.println(dailies);
        //x轴的数据, 也就是日期
        List<String> xAxisData = dailies.stream().map(Daily::getDateCalculated)
                .collect(Collectors.toList());
        //查询出来的人数
        List<Integer> data = dailies.stream().map((daily -> {
                    //switch不能带有数据类型
                    switch (queryDailyVo.getType()) {
                        case CourseNum:
                            return daily.getCourseNum();
                        case RegisterNum:
                            return daily.getRegisterNum();
                        case LoginNum:
                            return daily.getLoginNum();
                        case VideoViewNum:
                            return daily.getVideoViewNum();
                        default:
                            return null;
                    }
                })
        ).collect(Collectors.toList());
        map.put("xAxisData", xAxisData);
        map.put("data", data);
        return map;
    }
}
