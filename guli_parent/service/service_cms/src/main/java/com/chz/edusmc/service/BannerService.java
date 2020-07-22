package com.chz.edusmc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chz.edusmc.entity.domain.Banner;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author chz
 * @since 2020-07-20
 */
public interface BannerService extends IService<Banner> {
    /**
     * 查询存入缓存
     *
     * @return
     */
    @Cacheable(value = "bannerList", keyGenerator = "keyGenerator")
    List<Banner> getBannersTopDESC();
}
