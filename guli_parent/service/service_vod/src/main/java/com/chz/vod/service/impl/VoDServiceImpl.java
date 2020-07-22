package com.chz.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.chz.sms.utils.AliyunUtils;
import com.chz.vod.service.VoDService;
import com.chz.vod.util.VoDUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/17 21:05
 * @Description: TODO
 */
@Service
public class VoDServiceImpl implements VoDService {
    /**
     * 上传视频文件到aliyun
     *
     * @param file
     * @return 返回视频的id, 用于存储到数据库
     * @throws IOException
     */
    @Override
    public String uploadByVoD(MultipartFile file) {
        /*
        accessKeyId,
        accessKeySecret,
        title, 上传到aliyun上显示的名字
        filename, 上传的文件的原始名字
        inputStream, 上传的输入流
         */
        String filename = file.getOriginalFilename();
        assert filename != null;
        String title = filename.split("\\.")[0];
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UploadStreamRequest request = new UploadStreamRequest(
                AliyunUtils.KEY_ID,
                AliyunUtils.KEY_SECRET,
                title,
                filename,
                inputStream

        );
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        String videoId = null;
        if (response.isSuccess()) {
            videoId = response.getVideoId();
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            videoId = response.getVideoId();
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return videoId;
    }

    /**
     * 根据videoId删除aliyun中上传的视频
     *
     * @param videoSourceId
     */
    @Override
    public void deleteVoDByVideoSourceId(String videoSourceId) throws ClientException {
        DefaultAcsClient defaultAcsClient = VoDUtil.initVodClient();
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(videoSourceId);
        defaultAcsClient.getAcsResponse(request);
    }

    /**
     * 根据给出的id批量删除aliyun上的视频
     *
     * @param videoSourceIds
     * @throws ClientException
     */
    @Override
    public void deleteBatchVoDByVideoSourceId(List<String> videoSourceIds) throws ClientException {
        DefaultAcsClient defaultAcsClient = VoDUtil.initVodClient();
        DeleteVideoRequest request = new DeleteVideoRequest();
        String param = String.join(",", videoSourceIds);
        //注意该方法不是可变参数,而是使用字符串拼接的
        request.setVideoIds(param);
        defaultAcsClient.getAcsResponse(request);
    }
}
