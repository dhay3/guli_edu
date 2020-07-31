package com.chz.acl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/20 22:37
 * @Description: TODO
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AclApplication {
    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class, args);
    }
}
