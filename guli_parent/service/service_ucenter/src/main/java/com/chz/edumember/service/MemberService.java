package com.chz.edumember.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chz.edumember.entity.Member;
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


    /**
     * 检查数据库中是否有wechat用户
     * @param openid
     * @return
     */
    Member checkWechatUserByOpenId(String openid);

    Member saveWechatUserIfNotExist(String openid, String access_token);

    /**
     * 查询指定天数的注册人数
     * @param date
     * @return
     */
    int queryRegisterCountByDate(String date);
}
