package com.chz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 由于该模块继承service模块,所以也会继承数据库的依赖,springboot会autoConfig数据库的相关配置
 * 所以需要排除数据库的自动配置
 */
@EnableDiscoveryClient
//@SpringBootApplication(scanBasePackages = "com.chz")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
