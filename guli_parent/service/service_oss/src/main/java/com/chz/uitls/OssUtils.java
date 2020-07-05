package com.chz.uitls;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@ApiModel("oss工具类")
@Setter
@Component //要想自动配置必须通过@Component注入到ioc,@EnableConfigurationProperties无法使用
@PropertySource("classpath:oss-config.properties")
@ConfigurationProperties(prefix = "aliyun.oss.file")
public class OssUtils {
    //读取配置文件,静态属性无法接收值
    @ApiModelProperty("地域节点url")
    private String endpoint;
    @ApiModelProperty("accesskey-id")
    private String keyid;
    @ApiModelProperty("accesskey-password")
    private String keysecret;
    @ApiModelProperty("创建的bucket名字")
    private String bucketname;

    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;


    /**
     * 在构造器调用后调用, 效果同InitializingBean的afterPropertiesSet相同
     */
    @PostConstruct
    public void init() {
        END_POINT = endpoint;
        KEY_ID = keyid;
        KEY_SECRET = keysecret;
        BUCKET_NAME = bucketname;
    }

    /**
     * 创建Oss客户端对象
     */
    public static OSS createOssClient() {
        OSS ossClient = new OSSClientBuilder().build(END_POINT, KEY_ID, KEY_SECRET);
        //如果bucket不存在就新建一个
        if (!ossClient.doesBucketExist(BUCKET_NAME)) {
            ossClient.createBucket(BUCKET_NAME);
        }
        return ossClient;
    }

    public static String getEndPoint() {
        return END_POINT;
    }

    public static String getKeyId() {
        return KEY_ID;
    }

    public static String getKeySecret() {
        return KEY_SECRET;
    }

    public static String getBucketName() {
        return BUCKET_NAME;
    }
}
