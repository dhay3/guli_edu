package com.chz.edumember.controller;


import com.chz.edumember.entity.Member;
import com.chz.edumember.entity.vo.MemberLoginVo;
import com.chz.edumember.entity.vo.MemberRegisterVo;
import com.chz.edumember.entity.vo.MemberVo;
import com.chz.edumember.service.MemberService;
import com.chz.response.ResponseBo;
import com.chz.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author chz
 * @since 2020-07-23
 */
@Api("用户模块")
@RestController
@RequestMapping("/api/ucenter")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseBo register(@RequestBody MemberRegisterVo member) {
        memberService.register(member);
        return ResponseBo.ok();
    }

    @PostMapping("/login")
    public ResponseBo login(@RequestBody MemberLoginVo member) {
        //登入成功返回token值, 为了实现单点登入, 这里使用
        String token = memberService.login(member);
        return ResponseBo.ok().data("token", token);
    }

    /**
     * 根据header中的token获取用户信息
     *
     * @param request
     * @return
     */
    @GetMapping("/token")
    public ResponseBo getUserInfoByToken(HttpServletRequest request) {
        System.out.println(request.getHeader("token"));
        //调用jwt工具类获取header中的token
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //根据用户id获取用户信息
        Member member = memberService.getById(memberId);
        System.out.println(member);
        return ResponseBo.ok().data("user", member);
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseBo getUserInfoByUserId(@PathVariable String userId) {
        Member member = memberService.getById(userId);
        MemberVo memberVo = new MemberVo();
        BeanUtils.copyProperties(member, memberVo);
        return ResponseBo.ok().data("memberVo", memberVo);
    }

    /**
     * 由于使用了SQL自带函数, 所以需要使用xml
     *
     * @param date
     * @return
     */
    @ApiOperation("查询指定日期注册的人数")
    @GetMapping("/date/{date}")
    public ResponseBo queryRegisterCountByDate(@PathVariable String date) {
        int registerCount = memberService.queryRegisterCountByDate(date);
        return ResponseBo.ok().data("registerCount", registerCount);
    }

}

