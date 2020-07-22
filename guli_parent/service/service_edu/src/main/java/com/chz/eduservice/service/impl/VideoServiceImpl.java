package com.chz.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.eduservice.entity.domain.Video;
import com.chz.eduservice.feign.VoDClientFeign;
import com.chz.eduservice.mapper.VideoMapper;
import com.chz.eduservice.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author chz
 * @since 2020-06-24
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    /**
     * 在service中也可以直接调用feign
     */
    @Autowired
    private VoDClientFeign voDClientFeign;

    /**
     * 根据课程id查询edu_video,存在多个courseId相同的视频
     * 根据courseId删除aliyun上的视频
     *
     * @param courseId
     * @return
     */
    @Override
    public boolean deleteByCourseId(String courseId) {
        //根据课程id查询表中所有的aliyun上的视频id
        List<Video> videoList = baseMapper.selectList(new QueryWrapper<Video>().lambda()
                //这里的select表示只查询指定的字段,封装回Video中
                .eq(Video::getCourseId, courseId).select(Video::getVideoSourceId));
        //有可能查询出来的结果为null
        if (!Objects.equals(videoList, null)) {
            //使用流式api,但是存在可能videoSourceId为空的可能
            List<String> videoSourceIds = videoList.stream()
                    .map(Video::getVideoSourceId)
                    //只有返回条件为true的才会添加到stream中
                    .filter(videSourceId -> !Objects.equals(videSourceId, null))
                    .collect(Collectors.toList());
            if (videoSourceIds.size() > 0) {
                //调用方法删除aliyun上的视频
                voDClientFeign.deleteBatchVoDByVideoSourceId(videoSourceIds);
            }
        }

        return baseMapper.delete(new QueryWrapper<Video>().lambda()
                .eq(Video::getCourseId, courseId)) > 0;
    }
}
