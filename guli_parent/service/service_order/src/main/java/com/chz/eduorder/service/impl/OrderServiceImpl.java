package com.chz.eduorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.eduorder.entity.Order;
import com.chz.eduorder.feign.EduCourseClientFeign;
import com.chz.eduorder.feign.UcenterClientFeign;
import com.chz.eduorder.mapper.OrderMapper;
import com.chz.eduorder.service.OrderService;
import com.chz.exception.CusException;
import com.chz.response.ResponseBo;
import com.chz.statuscode.ResultCode;
import com.chz.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author chz
 * @since 2020-07-29
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private UcenterClientFeign ucenterClientFeign;
    @Autowired
    private EduCourseClientFeign eduCourseClientFeign;

    /**
     * 放回订单号
     *
     * @param courseId
     * @param memberId
     * @return
     */
    @Override
    public String createOrderByCourseIdMemberId(String courseId, String memberId) {
        //通过远程调用获取课程信息
        ResponseBo course = eduCourseClientFeign.getCourseById(courseId);
        Object courseInfoVo = course.getData().get("courseInfoVo");
        System.out.println(courseInfoVo);
        //通过远程调用获取用户信息
        ResponseBo userInfoByUserId = ucenterClientFeign.getUserInfoByUserId(memberId);
        Object memberVo = userInfoByUserId.getData().get("memberVo");
        System.out.println(memberVo);
        if (ObjectUtils.isEmpty(memberVo)) {
            throw new CusException("用户未登入", ResultCode.ERROR.val());
        }
        Order order = new Order();
        try {
            //由于memberVo是一个map所以可以通过反射获取到属性
            Method memberVoGet = memberVo.getClass().getMethod("get", Object.class);
            Method courseInfoVoGet = courseInfoVo.getClass().getMethod("get", Object.class);
            order.setOrderNo(OrderNoUtil.getOrderNo());
            order.setCourseId(courseId);
            order.setTeacherName(courseInfoVoGet.invoke(courseInfoVo, "teacherName").toString());
            order.setCourseCover(courseInfoVoGet.invoke(courseInfoVo, "cover").toString());
            order.setCourseTitle(courseInfoVoGet.invoke(courseInfoVo, "title").toString());

            order.setMemberId(memberId);
            order.setNickname(memberVoGet.invoke(memberVo, "nickname").toString());
            order.setMobile(memberVoGet.invoke(memberVo, "mobile").toString());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new CusException("方法不存在", ResultCode.ERROR.val());
        }
        baseMapper.insert(order);
        //返回生成的订单号
        return order.getOrderNo();
    }

}
