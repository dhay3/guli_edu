package com.chz.vod.util;

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.chz.utils.AliyunUtils;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/17 21:34
 * @Description: TODO
 */
//public class VoDUtil implements InitializingBean {
public class VoDUtil {
    private static final String REGION_ID = "shanghai";// 点播服务接入区域

    /**
     * 初始化vod方法
     *
     * @return
     * @throws ClientException
     */
    public static DefaultAcsClient initVodClient() throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID,
                AliyunUtils.KEY_ID,
                AliyunUtils.KEY_SECRET);
        return new DefaultAcsClient(profile);
    }
}
