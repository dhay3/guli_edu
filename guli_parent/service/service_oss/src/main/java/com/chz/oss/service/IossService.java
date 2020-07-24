package com.chz.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IossService {
    /**
     * 上传文件,返回上传到oss的url
     */
    String uploadFileAvatar(MultipartFile file) throws IOException;
}
