package com.chz.edumember.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.edumember.entity.Member;
import com.chz.edumember.entity.vo.MemberLoginVo;
import com.chz.edumember.entity.vo.MemberRegisterVo;
import com.chz.edumember.mapper.MemberMapper;
import com.chz.edumember.service.MemberService;
import com.chz.edumember.utils.HttpClientUtils;
import com.chz.edumember.utils.WechatUtils;
import com.chz.exception.CusException;
import com.chz.statuscode.ResultCode;
import com.chz.utils.JwtUtils;
import com.chz.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author chz
 * @since 2020-07-23
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public int queryRegisterCountByDate(String date) {
        return baseMapper.queryRegisterCountByDate(date);
    }

    /**
     * 根据手机号码和密码查询数据库
     *
     * @param member
     * @return token, 用于单点登入
     */
    @Override
    public String login(MemberLoginVo member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        //这里使用commons utils方法
        if (StringUtils.isAllEmpty(mobile, password)) {
            throw new CusException("手机号码或密码不能为空", ResultCode.ERROR.val());
        }
        //判断手机号码是否正确
        Member user = baseMapper.selectOne(new QueryWrapper<Member>().lambda()
                .eq(Member::getMobile, mobile));
        if (ObjectUtils.isEmpty(user)) {
            throw new CusException("手机号码或密码错误", ResultCode.ERROR.val());
        }
        //判断用户密码是否正确
        if (!MD5.encrypt(password).equals(user.getPassword())) {
            throw new CusException("手机号码或密码错误", ResultCode.ERROR.val());
        }
        //判断用户是否被禁用
        if (user.getDisabled()) {
            throw new CusException("账号被禁用", ResultCode.ERROR.val());
        }
        //放回含有信息的token
        return JwtUtils.getJwtToken(user.getId(), user.getNickname());
    }

    @Override
    public boolean register(MemberRegisterVo member) {
        //手机验证码
        String code = member.getCode();
        String mobile = member.getMobile();
        String nickname = member.getNickname();
        String password = member.getPassword();
        //设置加密密码到数据库
        member.setPassword(MD5.encrypt(password));
        if (StringUtils.isAllEmpty(code, mobile, nickname, password)) {
            throw new CusException("注册失败", ResultCode.ERROR.val());
        }
        if (!validateCode(mobile, code)) {
            throw new CusException("验证码错误", ResultCode.ERROR.val());
        }
        //查询数据库. 如果库中有相同的手机号, 就禁止用户注册
        Integer cnt = baseMapper.selectCount(new QueryWrapper<Member>().lambda()
                .eq(Member::getMobile, mobile));
        if (cnt > 0) {
            throw new CusException("用户已存在", ResultCode.ERROR.val());
        }
        Member res = new Member();
        BeanUtils.copyProperties(member, res);
        //添加数据到数据库
        return baseMapper.insert(res) == 0;
    }


    /**
     * @param openid
     * @return 如果存在返回true, 不存在返回false
     */
    @Override
    public Member checkWechatUserByOpenId(String openid) {
        return baseMapper.selectOne(new QueryWrapper<Member>().lambda()
                .eq(Member::getOpenid, openid));
    }

    @Override
    public Member saveWechatUserIfNotExist(String openid, String access_token) {
        Member member = checkWechatUserByOpenId(openid);
        if (ObjectUtils.isEmpty(checkWechatUserByOpenId(openid))) {
            String userInfoUrlByAccessToken = WechatUtils.getUserInfoUrlByAccessToken(access_token);
            String userInfo = null;
            try {
                userInfo = HttpClientUtils.get(userInfoUrlByAccessToken);
            } catch (Exception e) {
                e.printStackTrace();
                throw new CusException("换取用户信息失败", ResultCode.ERROR.val());
            }
            System.out.println(userInfo);
            member = new Member();
            //注意这里不能使用String, Sex会封装成Double
            Map<String, Object> userInfoMap = WechatUtils.castJsonInfoToMap(userInfo);
            /*
            返回响应体中包含openid,nickname,sex,headimgurl...
            */
            //唯一标识
            member.setOpenid(userInfoMap.get("openid").toString())
                    //名称
                    .setNickname(userInfoMap.get("nickname").toString())
                    //头像
                    .setAvatar(userInfoMap.get("headimgurl").toString())
                    //性别,这里拿出来的是 1.0
                    .setSex(Integer.valueOf(userInfoMap.get("sex").toString().split("\\.")[0]));
            //将微信用户信息存入数据库
            baseMapper.insert(member);
        }
        return member;
    }

    /**
     * 判断与redis中存储的验证码是否相同
     *
     * @param phoneNumber
     * @param code
     * @return
     */
    private boolean validateCode(String phoneNumber, String code) {
        return code.equals(redisTemplate.opsForValue().get(phoneNumber));
    }


}
