package com.chz.eduservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chz.eduservice.entity.domain.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chz.eduservice.entity.vo.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author chz
 * @since 2020-05-21
 */
public interface TeacherService extends IService<Teacher> {
    /**
     * 该方法应该返回page对象,而不是list集合, 因为如果返回的是list集合
     * total并不是limit后的结果, 而是所有符合条件的结果
     * 如果不需要返回vo可以直接使用
     *
     * @param currentPage
     * @param pageSize
     * @param teacherQuery
     * @return
     */
    IPage<Teacher> pageTeacherCondition(Integer currentPage, Integer pageSize, TeacherQuery teacherQuery);
}
