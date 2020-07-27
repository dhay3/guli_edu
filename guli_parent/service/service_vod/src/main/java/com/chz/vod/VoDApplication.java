package com.chz.vod;

import com.chz.servicebase.config.JacksonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/17 20:42
 * @Description: 即使引入了dependency, 同样还是需要扫描包, 来使用swagger
 * 且同时还需要扫描到前包否则将报错
 * 由于配置了数据库, 还需要将数据库自动配置除外
 * exclude属性只能排除AutoConfiguration类, 由于JacksonConfig是手动注入属性的
 * 这里可以通过excludeFilters排除单独的类扫描
 * ASSIGNABLE_TYPE指定类型
 * ANNOTATION表有指定注解的类
 */
@EnableDiscoveryClient
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = JacksonConfig.class)})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, scanBasePackages = {
        "com.chz.vod", "com.chz.servicebase"
})
public class VoDApplication {
    public static void main(String[] args) {
        SpringApplication.run(VoDApplication.class, args);
    }
}
