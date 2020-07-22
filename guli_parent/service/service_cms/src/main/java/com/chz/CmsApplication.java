package com.chz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/20 19:00
 * @Description: TODO
 */
@EnableCaching//开启缓存
@EnableDiscoveryClient
@SpringBootApplication(
        scanBasePackages = {"com.chz.edusmc", "com.chz.servicebase"})
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
