package com.chz.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/17 20:42
 * @Description: 即使引入了dependency, 同样还是需要扫描包, 来使用swagger
 * 且同时还需要扫描到前包否则将报错
 * 由于配置了数据库, 还需要将数据库自动配置除外
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class, scanBasePackages = {
        "com.chz.vod", "com.chz.servicebase"
})
public class VoDApplication {
    public static void main(String[] args) {
        SpringApplication.run(VoDApplication.class, args);
    }
}
