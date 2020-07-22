package com.chz.sms.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.chz.sms.service.IossService;
import com.chz.uitls.OssUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
//@Order(Ordered.LOWEST_PRECEDENCE)这里无法通过Ordered来解决注入顺序
public class OssServiceImpl implements IossService {
    //无法通过属性来获取bucketName,因为注入顺序不定,如果OssServiceImpl先注入就会拿到null
//    String BUCKET_NAME = OssUtils.BUCKET_NAME;
//    String BUCKET_NAME = OssUtils.getBucketName();//同样的因为注入的顺序不定,所以调用实例方法也相同

    /**
     * 上传头像到oss
     * 参考文档
     * https://help.aliyun.com/document_detail/84781.html?spm=a2c4g.11186623.6.794.680d2606MvIKQd
     */
    @Override
    public String uploadFileAvatar(MultipartFile file) throws IOException {
        OSS ossClient = OssUtils.createOssClient();
        //获取文件名
        String filename = file.getOriginalFilename();
        //在文件名称中添加一个随机唯一的值,生成的uuid会携带-,
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //拼接文件名,为了之后相同文件名的将前面的相同文件名的文件覆盖
        filename = uuid + filename;

        //将文件按照日期分类
        // 2020/06/20/01.jpg oss中将会自动生成 2020/06/20三个层级文件
        //joda依赖
        String datePath = new DateTime().toString("yyyy/MM/dd");
        filename = datePath + "/" + filename;

        //获取文件的流
        InputStream inputStream = file.getInputStream();

        PutObjectRequest putObjectRequest = new PutObjectRequest(OssUtils.getBucketname(), filename, inputStream);
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
        /*按照url地址规则拼接url
        https://gulied-program.oss-cn-beijing.aliyuncs.com/2.jpg
        */
        return "https://" + OssUtils.getBucketname() + "." + OssUtils.getEndpoint() + "/" + filename;
    }
}
