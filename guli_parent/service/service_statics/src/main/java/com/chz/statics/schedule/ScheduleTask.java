package com.chz.statics.schedule;

import com.chz.statics.service.DailyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author: CHZ
 * @DateTime: 2020/8/1 18:08
 * @Description: 定时任务
 */
@Slf4j
@Component
public class ScheduleTask {
    @Autowired
    private DailyService dailyService;

    /**
     * 每天凌晨一点调用定时任务
     * 将前一天的数据存入数据库中
     */
    @Scheduled(cron = "* * 1 * * ?")
    public void test() {
        String date = DateTimeFormatter
                .ofPattern("yyyy-MM-dd")
                //获取前一天
                .format(LocalDate.now().plusDays(-1));
        log.info("定时任务调用/{}", date);
        dailyService.statisticsRegisterCountByDate(date);
    }

//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        taskRegistrar.addCronTask(new CronTask(()-> System.out.println("hello world"),
//                "* * * 2 8 ?"));
//    }
}
