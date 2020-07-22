package com.chz.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/22 20:54
 * @Description: TODO
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,
        scanBasePackages = {"com.chz.sms", "com.chz.servicebase"})
public class SMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(SMSApplication.class, args);
    }
}
