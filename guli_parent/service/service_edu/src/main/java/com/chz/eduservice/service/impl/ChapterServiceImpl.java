package com.chz.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chz.eduservice.entity.domain.Chapter;
import com.chz.eduservice.entity.domain.Video;
import com.chz.eduservice.entity.vo.ChapterVo;
import com.chz.eduservice.entity.vo.VideoVo;
import com.chz.eduservice.mapper.ChapterMapper;
import com.chz.eduservice.mapper.VideoMapper;
import com.chz.eduservice.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.exception.CusException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author chz
 * @since 2020-06-25
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Autowired
    private VideoMapper videoMapper;

    /**
     * @param CourseId 根据课程的id获取对应的所有信息
     * @return 返回封装对应关系的vo
     */
    @Override
    public List<ChapterVo> getChapterAndVideoById(String CourseId) {
        //由于chapterId不同第一种方法需要访问多次数据库
        //查询对应courseId所有的chapter
//        List<Chapter> chapters = baseMapper.selectList(new QueryWrapper<Chapter>().lambda()
//                .eq(Chapter::getCourseId, CourseId));
//        ArrayList<ChapterVo> chapterVoList = new ArrayList<>();
//        for (Chapter chapter : chapters) {
//            ChapterVo chapterVo = new ChapterVo();
//            BeanUtils.copyProperties(chapter, chapterVo);
//            //获取回显的id
//            String chapterId = chapter.getId();
//            //根据chapterId查询对应的video
//            List<Video> videos = videoMapper.selectList(new QueryWrapper<Video>().lambda()
//                    .eq(Video::getChapterId, chapterId));
//            ArrayList<VideoVo> videoVoList = new ArrayList<>();
//            for (Video video : videos) {
//                VideoVo videoVo = new VideoVo();
//                BeanUtils.copyProperties(video, videoVo);
//                videoVoList.add(videoVo);
//            }
//            //封装video信息
//            chapterVo.setChildren(videoVoList);
//            chapterVoList.add(chapterVo);
//        }

        //查询chapter表对应courseId的所有结果集
        final List<Chapter> chapters = baseMapper.selectList(new QueryWrapper<Chapter>().lambda()
                .eq(Chapter::getCourseId, CourseId));
        final ArrayList<ChapterVo> chapterVoList = new ArrayList<>();
        //查询video表对应courseId的所有结果集
        List<Video> videos = videoMapper.selectList(new QueryWrapper<Video>().lambda()
                .eq(Video::getCourseId, CourseId));
        chapters.forEach(chapter -> {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            ArrayList<VideoVo> videoVoList = new ArrayList<>();
            videos.forEach(video -> {
                if (video.getChapterId().equals(chapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoList.add(videoVo);
                }
            });
            chapterVo.setChildren(videoVoList);
            chapterVoList.add(chapterVo);
        });
        return chapterVoList;
    }

    /**
     * 如果章节小有小节就不能删除
     * 如果章节下没有小节才能删除
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteChapterOnCasecade(String id) {
        //根据章节id查询小节
        //如果能查询除结果就不能删除,反之删除
        //不需要查询具体结果, 只需要查询是否有记录
        Integer res = videoMapper.selectCount(new QueryWrapper<Video>().lambda()
                .eq(Video::getChapterId, id));
        if (res > 0)
            throw new CusException("不能删除", 20001);
        //如果删除成功返回true, 否则false
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    public boolean deleteChapterByCourseId(String courseId) {
        return baseMapper.delete(new QueryWrapper<Chapter>().lambda()
                .eq(Chapter::getCourseId, courseId)) > 0;
    }
}
