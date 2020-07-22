package com.chz.edusmc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.edusmc.entity.domain.Banner;
import com.chz.edusmc.mapper.BannerMapper;
import com.chz.edusmc.service.BannerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author chz
 * @since 2020-07-20
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {
    /**
     * 查询结果, 并讲结果缓存到redis中
     *
     * @return
     */
    @Override
    public List<Banner> getBannersTopDESC() {
        //拼接sql, 查询降序排序的前4条记录
        //SELECT * FROM cmr_banner ORDER BY id ASC LIMIT 4
        List<Banner> banners = baseMapper.selectList(new QueryWrapper<Banner>().lambda()
                .orderByDesc(Banner::getSort).last("limit 4"));
        return banners;
    }
}
