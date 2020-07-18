package com.chz.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.eduservice.entity.domain.Video;
import com.chz.eduservice.mapper.VideoMapper;
import com.chz.eduservice.service.VideoService;
import org.springframework.stereotype.Service;

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
    @Override
    public boolean deleteByCourseId(String courseId) {
        return baseMapper.delete(new QueryWrapper<Video>().lambda()
                .eq(Video::getCourseId, courseId)) > 0;
    }
}
