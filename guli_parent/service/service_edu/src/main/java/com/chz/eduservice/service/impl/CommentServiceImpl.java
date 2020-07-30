package com.chz.eduservice.service.impl;

import com.chz.eduservice.entity.domain.Comment;
import com.chz.eduservice.mapper.CommentMapper;
import com.chz.eduservice.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author chz
 * @since 2020-07-28
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
