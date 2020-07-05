package com.chz.eduservice.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Configuration
//@MapperScan扫描的是接口,不包括xml文件,如果需要扫描xml文件请在yml中配置mapperLocations
@MapperScan("com.chz.eduservice.mapper")
public class MPConfig {
    /**
     * 分页插件及防止恶意sql注入
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //设置数据库类型
        paginationInterceptor.setDbType(DbType.MYSQL);
        //防止恶意sql注入
        ArrayList<ISqlParser> sqlParsers = new ArrayList<>();
        sqlParsers.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParsers);
        return paginationInterceptor;
    }

    /**
     * 乐观锁插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 自动填充插件
     */
    @Bean
    public MyMetaObjectHandler objectHandler() {
        return new MyMetaObjectHandler();
    }

    @Slf4j
    private static class MyMetaObjectHandler implements MetaObjectHandler {
        /**
         * insert 自动填充
         */
        @Override
        public void insertFill(MetaObject metaObject) {
            log.info("autoFill ===> {}", "insert");
            // 起始版本 3.3.0(推荐使用), 对应DO的属性
            this.strictInsertFill(metaObject, "gmtCreate", LocalDateTime.class, LocalDateTime.now());
            //这里同样需要添加gmtModified
            this.strictUpdateFill(metaObject, "gmtModified", LocalDateTime.class, LocalDateTime.now());
        }

        /**
         * update 自动填充
         */
        @Override
        public void updateFill(MetaObject metaObject) {
            log.info("autoFill ===> {}", "update");
            this.strictUpdateFill(metaObject, "gmtModified", LocalDateTime.class, LocalDateTime.now());
        }
    }
}
