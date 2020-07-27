package com.chz.eduservice.service;

import com.chz.eduservice.entity.domain.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chz.eduservice.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author chz
 * @since 2020-06-25
 */
public interface ChapterService extends IService<Chapter> {
    /**
     * @param courseId 根据课程的id获取对应的所有信息
     * @return 返回封装对应关系的vo层
     */
    List<ChapterVo> getChapterAndVideoByCourseId(String courseId);

    /**
     * 删除章节
     * 如果章节下面有小节, 不能删除章节, 只有章节下没有小节才能删除
     *
     * @param id
     * @return
     */
    boolean deleteChapterOnCasecade(String id);

    boolean deleteChapterByCourseId(String courseId);
}
