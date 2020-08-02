package com.chz.edumember.mapper;

import com.chz.edumember.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author chz
 * @since 2020-07-23
 */
public interface MemberMapper extends BaseMapper<Member> {
    /**
     * int会自动封装
     *
     * @param date
     * @return
     */
    int queryRegisterCountByDate(String date);
}
