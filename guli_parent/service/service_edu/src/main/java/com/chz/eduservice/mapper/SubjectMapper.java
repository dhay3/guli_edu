package com.chz.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chz.eduservice.entity.domain.Subject;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author chz
 * @since 2020-06-23
 */
public interface SubjectMapper extends BaseMapper<Subject> {

    /**
     *
     * @param title
     * @return
     */
    List<String> getSubTitlesByParentTitle(String title);
}
