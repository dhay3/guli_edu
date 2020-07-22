package com.chz.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chz.eduservice.entity.domain.Subject;
import com.chz.eduservice.entity.excel.SubjectData;
import com.chz.eduservice.service.SubjectService;
import com.chz.utils.CusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * @Author: CHZ
 * @DateTime: 2020/6/23 11:38
 * @Description: SubjectData监听器
 * 该类无法交给spring管理,所以无法通过@AutoWire或是其他方法获取spring中的bean
 * 需要手动注入bean
 */
@Slf4j
public class SubjectDataListener extends AnalysisEventListener<SubjectData> {
    private SubjectService subjectService;

    public SubjectDataListener() {
    }

    /**
     * 由于此类无法通过spring管理, 所以需要手动注入service层或dao层
     * 实际是通过controller层调用该方法, 然后传入service
     *
     * @param subjectService service层
     */
    public SubjectDataListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * 一行一行读取,不包含表头, 第一个值为一级分类, 第二值为二级分类
     * 没读取一行,就将数据放入数据库中
     * 如果数据库中存在值, 就不添加, 反之添加
     * 一行xslx 包括一级分类,二级分类
     *
     * @param data 就是封装的实体类
     */
    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
        //数据库中添加第一列
        //如果数据为空直接抛出异常
        if (ObjectUtils.isEmpty(data)) {
            throw new CusException("数据为空", 20001);
        }
        //根据一级subject,查询数据库中是否存在数据
        Subject subjectOne = existOneSubject(data.getOneSubjectName());
        //比较excel中的一级subject与数据库中的一级subject
        if (Objects.equals(subjectOne, null)) {
            subjectOne = new Subject();
            //一级分类pid为0
            subjectOne.setParentId("0");
            subjectOne.setTitle(data.getOneSubjectName());
            //添加一级分类到数据库
            //添加了一级分类时,由于设置了主键策略会自动添加id
            subjectService.save(subjectOne);
        }

        //数据库中添加第二列
        //创建二级的subject的前提是由一级subject
        //根据excel中的一级subject获取id,作为二级subject的pid
        String pid = subjectOne.getId();
        //如果二级subject为null添加数据
        if (Objects.equals(existTwoSubject(data.getTwoSubjectName(), pid), null)) {
            Subject subject = new Subject();
            subject.setTitle(data.getTwoSubjectName());
            //二级subject的pid就是一级subject的id
            subject.setParentId(pid);
            subjectService.save(subject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("analysis down");
    }

    /**
     * 根据name判断是否有一级分类
     * SELECT * FROM edu_subject WHERE title = ? AND parent_id = 0;
     * 如果存在就不添加, 如果不存在就添加
     *
     * @param name 对应title
     * @return subject实体类, 为了方便获取当前对象的id
     */
    private Subject existOneSubject(String name) {
        return subjectService.getOne(new QueryWrapper<Subject>().lambda()
                .eq(Subject::getTitle, name)
                .eq(Subject::getParentId, "0"));
    }

    /**
     * 判断是否有重复的二级分类
     * title相同,且pid相同就不添加
     *
     * @param name
     * @return
     */
    private Subject existTwoSubject(String name, String pid) {
        return subjectService.getOne(new QueryWrapper<Subject>().lambda()
                .eq(Subject::getTitle, name)
                .eq(Subject::getParentId, pid));
    }

}
