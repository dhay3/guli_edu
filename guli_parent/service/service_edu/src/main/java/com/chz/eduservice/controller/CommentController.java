package com.chz.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chz.eduservice.entity.domain.Comment;
import com.chz.eduservice.feign.UcenterClientFeign;
import com.chz.eduservice.service.CommentService;
import com.chz.exception.CusException;
import com.chz.response.ResponseBo;
import com.chz.statuscode.ResultCode;
import com.chz.utils.JwtUtils;
import com.chz.utils.MPUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author chz
 * @since 2020-07-28
 */
@Validated
@RestController
@RequestMapping("/eduservice/comment")
public class CommentController {
    @Autowired
    private UcenterClientFeign ucenterClientFeign;
    @Autowired
    private CommentService commentService;

    @ApiOperation("分页查询课程评论")
    @GetMapping("/{courseId}/{currentPage}/{pageSize}")
    public ResponseBo pageComments(@PathVariable @Min(1) Integer currentPage,
                                   @PathVariable Integer pageSize,
                                   @PathVariable String courseId) {
        Page<Comment> page = new Page<Comment>(currentPage, pageSize);
        commentService.page(page, new QueryWrapper<Comment>().lambda()
                .orderByDesc(Comment::getGmtCreate).eq(Comment::getCourseId, courseId));
        Map<String, Object> infos = MPUtils.getPageInfos(page);
        return ResponseBo.ok().data(infos);
    }

    /**
     * 由于使用JWT, request header中包含token
     * 根据token获取用户id, 然后根据用户id获取用户信息
     *
     * @param comment
     * @param request
     * @return
     */
    @ApiOperation("添加评论")
    @PostMapping
    public ResponseBo saveComment(@RequestBody Comment comment, HttpServletRequest request) {
        //获取用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //调用运程接口,获取用户信息
        //由于是通过controller返回的对象, 返回的对象会以k-v对的map形式存储, 获得的Class也是HashMap
        Object memberVo = ucenterClientFeign.getUserInfoByUserId(memberId)
                .getData().get("memberVo");
        if (ObjectUtils.isEmpty(memberVo)) return ResponseBo.error().message("未登入");
        try {
            //由于memberVo是一个map所以可以通过反射获取到属性
            Method get = memberVo.getClass().getMethod("get", Object.class);
            comment.setNickname(get.invoke(memberVo, "nickname").toString());
            comment.setAvatar(get.invoke(memberVo, "avatar").toString());
            comment.setMemberId(get.invoke(memberVo, "id").toString());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new CusException("方法不存在", ResultCode.ERROR.val());
        }
        System.out.println(comment);
        return commentService.save(comment) ? ResponseBo.ok() : ResponseBo.error();
    }

//    @GetMapping
//    public ResponseBo test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        //由于是通过controller返回的对象, 返回的对象会以k-v对的map形式存储, 获得的Class也是HashMap
//        Object memberVo = ucenterClientFeign.getUserInfoByUserId("1086387099701100545")
//                .getData().get("memberVo");
//        Method get = memberVo.getClass().getMethod("get", Object.class);
//        Comment comment = new Comment();
//        comment.setNickname(get.invoke(memberVo, "nickname").toString());
//        comment.setAvatar(get.invoke(memberVo, "avatar").toString());
//        comment.setMemberId(get.invoke(memberVo, "id").toString());
//        System.out.println(comment);
//        return ResponseBo.ok().data("commet", comment);
//    }
}

