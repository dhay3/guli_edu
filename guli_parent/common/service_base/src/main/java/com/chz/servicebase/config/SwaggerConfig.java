package com.chz.servicebase.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类
 * 访问地址 http://localhost:8002/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String VERSION = "1.1";


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(apiInfo())
                //会自动扫描当前模块下使用了Swagger2注解的类
                .select()
                //这些都是springboot中默认的一些url, 需要忽略
                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    /**
     * 设置api的信息 ,这些信息会展示在文档中
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("网站-课程中心Api文档")
                .description("本文档描述了课程中心微服务接口定义")
                .contact(new Contact("chz", "https://www.chz.com", "kikochz@163.com"))
                .version(VERSION)
                .build();
    }
}
