package com.chz.eduorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/29 16:55
 * @Description: 订单服务模块
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(
        scanBasePackages = {"com.chz.eduorder", "com.chz.servicebase"})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
