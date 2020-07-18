package com.chz.vod.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/17 21:05
 * @Description: TODO
 */
public interface VoDService {
    String uploadByVoD(MultipartFile file);

    void deleteVoDByVideoId(String videoId) throws ClientException;
}
