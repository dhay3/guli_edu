package com.chz.eduorder.service.impl;

import com.chz.eduorder.entity.PayLog;
import com.chz.eduorder.mapper.PayLogMapper;
import com.chz.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author chz
 * @since 2020-07-29
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
