package com.chz.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.eduservice.entity.domain.Subject;
import com.chz.eduservice.entity.excel.SubjectData;
import com.chz.eduservice.entity.vo.NestedSubjectVo;
import com.chz.eduservice.entity.vo.SubjectVo;
import com.chz.eduservice.listener.SubjectDataListener;
import com.chz.eduservice.mapper.SubjectMapper;
import com.chz.eduservice.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author chz
 * @since 2020-06-23
 */
@Service
@Slf4j
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
    /**
     * 可以使用ServiceImpl中的protected M baseMapper; 无需注入SubjectMapper
     */
//    @Autowired
//    SubjectMapper subjectMapper;

    /**
     * 添加课程分类
     * 将xslx中读取到数据添加到数据库中, 然后通过调用sql回显数据到页面
     * @param file 需要上传的excel
     * @return 是否添加成功
     */
    @Override
    public boolean saveSubject(MultipartFile file, SubjectService subjectService) {
        try {
            InputStream fileInputStream = file.getInputStream();
            //这里每读取到一行,就将数据放入到数据库中,doRead方法会打印出数据
            EasyExcel.read(fileInputStream, SubjectData.class, new SubjectDataListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取excel文件错误");
            return false;
        }
        return true;
    }

    /**
     * 查询数据库获取结果
     * subjectVo 一级分类
     * NestedSubjectVo 二级分类
     */
    @Override
    public List<SubjectVo> getAllOneTwoSubject() {
        //1.查询所有的一级分类
        List<Subject> subjectVos = baseMapper
                .selectList(new QueryWrapper<Subject>()
                        .lambda()
                        //一级分类pid == 0
                        .eq(Subject::getParentId, "0"));
        //2.查询所有的二级分类
        List<Subject> NestedSubjectVos = baseMapper
                .selectList(new QueryWrapper<Subject>()
                        .lambda()
                        //二级分类 pid != 0
                        //不等于ne (not equal)
                        .ne(Subject::getParentId, "0")
                );
        //3.封装一级分类
        ArrayList<SubjectVo> subjectVoList = new ArrayList<>();
        for (Subject subject : subjectVos) {
            SubjectVo subjectVo = new SubjectVo();
            //使用spring的工具类,将subject中的所有属性set到subjectVo中
            //如果subject中没有对应的值,不做封装, 只找到对应的值做封装
            BeanUtils.copyProperties(subject, subjectVo);
            ArrayList<NestedSubjectVo> nestedSubjectVoList = new ArrayList<>();
            //4. 封装二级分类
            for (Subject nestedSubjectVo : NestedSubjectVos) {
                String voPid = nestedSubjectVo.getParentId();
                //mp会回显id
                String pid = subject.getId();
                //如果二级分类的pid和一级分类的id相等
                if (Objects.equals(voPid, pid)) {
                    NestedSubjectVo child = new NestedSubjectVo();
                    //赋值二级分类中的属性到child中
                    BeanUtils.copyProperties(nestedSubjectVo, child);
                    nestedSubjectVoList.add(child);
                }
            }
            //设置二级分类
            subjectVo.setChildren(nestedSubjectVoList);
            subjectVoList.add(subjectVo);
        }
        return subjectVoList;
    }
}
