package com.chz.eduservice.service;

import com.chz.eduservice.entity.domain.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chz.eduservice.entity.vo.SubjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author chz
 * @since 2020-06-23
 */
public interface SubjectService extends IService<Subject> {
    /**
     * 添加课程分类
     */
    boolean saveSubject(MultipartFile file,SubjectService subjectService);

    List<SubjectVo> getAllOneTwoSubject();
}
