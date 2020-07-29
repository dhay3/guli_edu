package com.chz.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.eduservice.entity.domain.Teacher;
import com.chz.eduservice.entity.query.TeacherQuery;
import com.chz.eduservice.mapper.TeacherMapper;
import com.chz.eduservice.service.TeacherService;
import com.chz.utils.MPUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author chz
 * @since 2020-05-21
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public IPage<Teacher> pageTeacherCondition(Integer currentPage, Integer pageSize, TeacherQuery teacherQuery) {
        LambdaQueryWrapper<Teacher> wrapper = new QueryWrapper<Teacher>().lambda();
        //当发送空串时使用{}
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //动态sql
        if (!StringUtils.isEmpty(name)) {
            wrapper.like(Teacher::getName, name);
        }
        if (!ObjectUtils.isEmpty(level)) {
            wrapper.like(Teacher::getLevel, level);
        }
        //大于等于开始时间
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge(Teacher::getGmtCreate, begin);
        }
        //小于等于结束时间
        if (!StringUtils.isEmpty(end)) {
            wrapper.le(Teacher::getGmtModified, end);
        }
        //按时间排序
        wrapper.orderByDesc(Teacher::getGmtCreate);
        IPage<Teacher> teacherPage = new Page<>(currentPage, pageSize);
        //结果会封装进teacherPage, 无需返回
        baseMapper.selectPage(teacherPage, wrapper);
        return teacherPage;
    }

    /**
     * 按照id查询排名前四的讲师
     *
     * @return
     */
    @Override
    public List<Teacher> getTopTeachersDESC() {
        List<Teacher> teachers = baseMapper.selectList(new QueryWrapper<Teacher>().lambda()
                .orderByDesc(Teacher::getId).last("limit 4"));
        return teachers;
    }

    @Override
    public Map<String, Object> pageTeachersFront(Page<Teacher> page) {
        LambdaQueryWrapper<Teacher> wrapper = new QueryWrapper<Teacher>().lambda()
                .orderByDesc(Teacher::getId);
        baseMapper.selectPage(page, wrapper);
        return MPUtils.getPageInfos(page);
    }
}
