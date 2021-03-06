package com.chz;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.chz.educms.controller.BannerCustomController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/20 18:29
 * @Description: TODO
 */
@SpringBootTest
public class CmsTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private BannerCustomController customController;

    @Test
    void test() throws SQLException {

    }

    @Test
    void generator() {
        //项目路径
        System.out.println(System.getProperty("user.dir"));

        //代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        //1.全局配置 调用generator.config下的
        GlobalConfig gc = new GlobalConfig();
        //获取当前项目的路径
        String path = System.getProperty("user.dir");
        //设置是否开启AR
//        gc.setActiveRecord()
        gc.setAuthor("chz")
                //文件输出路径
                .setOutputDir(path + "/src/main/java")
                //生成时是否打开文件
                .setOpen(false)
                //是否覆盖文件
                .setFileOverride(false)
                //设置主键策略,采用UUID
                .setIdType(IdType.ASSIGN_ID)
                //DO中日期类的类型, 缺省值为TIME_PACK
                .setDateType(DateType.TIME_PACK)
                //是否开启resultMap,默认false
                .setBaseResultMap(true)
                //是否开启sql片段,默认false
                .setBaseColumnList(true)
                //去掉service接口首字母的I, 如DO为User则叫UserService
                .setServiceName("%sService")
                //开启Swagger2
                .setSwagger2(true);


        //2.数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        //设置数据源类型
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/gedu?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai")
                .setUsername("root")
                .setPassword("12345");

        //3.策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        //是否开启大写命名,默认不开启
        strategyConfig.setCapitalMode(false)
                //生成的DO自动实现Serializable接口, 默认true
                .setEntitySerialVersionUID(true)
                //数据库 表 映射到实体类命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                //数据库表 字段 映射到实体类的命名策略
                .setColumnNaming(NamingStrategy.underline_to_camel)
                //设置想要生成的表
                .setInclude("crm_banner")
                //生成的dao,service,entity不再带tbl_前缀
                .setTablePrefix("crm")
                //设置lombok, @Accessor(chain = true),@Data等
                .setEntityLombokModel(true)
                //controller使用@RestController
                .setRestControllerStyle(true)
                //Mapping驼峰转连字
                .setControllerMappingHyphenStyle(true)
                //自动填充字段,对应数据库中的字段
                .setTableFillList(Arrays
                        .asList(new TableFill("gmt_create", FieldFill.INSERT),
                                new TableFill("gmt_modified", FieldFill.INSERT_UPDATE)))
                //.setVersionFieldName("")//乐观锁属性名
                //表中字段为is_deleted,生成的DO中去掉is前缀
                .setEntityBooleanColumnRemoveIsPrefix(true)
                //对应数据库中的字段
                .setLogicDeleteFieldName("is_deleted");//逻辑删除属性名


        //4.包配置
        PackageConfig packageConfig = new PackageConfig();
        //setParent设置统一的包路径
        //设置模块名,对应controller中使用eduservice作为url, 如@RequestMapping("/eduservice/teacher"), 所有生成的都会在以该模块名为的包下
        packageConfig.setModuleName("edusmc")
                .setParent("com.chz")
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("entity")
                .setXml("mapper");

        //整合配置
        autoGenerator.setPackageInfo(packageConfig)
                .setDataSource(dataSourceConfig)
                .setGlobalConfig(gc)
                .setStrategy(strategyConfig);
        //执行
        autoGenerator.execute();
    }
}
