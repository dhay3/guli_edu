package com.chz.statics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: CHZ
 * @DateTime: 2020/8/1 14:38
 * @Description: TODO
 */
@EnableScheduling//表示开启定时任务
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.chz.statics", "com.chz.servicebase"})
public class StaticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaticsApplication.class, args);
    }
}
