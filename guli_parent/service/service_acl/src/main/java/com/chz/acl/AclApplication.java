package com.chz.acl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: CHZ
 * @DaeTime: 2020/7/20 22:37
 * @Description: TODO
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {
        "com.chz.acl","com.chz.servicebase","com.chz.security"
})
public class AclApplication {
    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class, args);
    }
}
