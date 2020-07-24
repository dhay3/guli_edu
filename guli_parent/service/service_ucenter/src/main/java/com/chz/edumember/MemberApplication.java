package com.chz.edumember;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/23 23:20
 * @Description: TODO
 */
@EnableDiscoveryClient
@SpringBootApplication(
        scanBasePackages = {"com.chz.edumember", "com.chz.servicebase"}
)
public class MemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }
}
