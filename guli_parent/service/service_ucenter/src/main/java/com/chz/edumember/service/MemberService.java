package com.chz.edumember.service;

import com.chz.edumember.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chz.edumember.entity.vo.MemberLoginVo;
import com.chz.edumember.entity.vo.MemberRegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author chz
 * @since 2020-07-23
 */
public interface MemberService extends IService<Member> {

    String login(MemberLoginVo member);

    boolean register(MemberRegisterVo member);
}
