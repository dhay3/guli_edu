package com.chz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 即使引入dependency, 还是需要扫描, 相当于@ComponentScan扫描当前包及其子包
 * 由于swagger没有启动类,所以需要扫描当前项目和swagger配置项目
 * 否则无法在该模块下使用swagger
 */
@EnableCaching
@EnableFeignClients//如果在同一个模块中就无需使用basePackages
@EnableDiscoveryClient//注册服务到nacos
@SpringBootApplication(
        scanBasePackages = {"com.chz.eduservice", "com.chz.servicebase"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
