package com.chz.vod.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/17 21:05
 * @Description: TODO
 */
public interface VoDService {
    String uploadByVoD(MultipartFile file);

    void deleteVoDByVideoSourceId(String videoId) throws ClientException;

    /**
     * 批量删除aliyun上的视频
     *
     * @param videoIds
     */
    void deleteBatchVoDByVideoSourceId(List<String> videoIds) throws ClientException;
}
