package com.chz.educms.controller;


import com.chz.educms.entity.domain.Banner;
import com.chz.educms.service.BannerService;
import com.chz.response.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author chz
 * @since 2020-07-20
 */
@Api("前台轮播图")
@RestController
@RequestMapping("/educms/bannercustom")
public class BannerCustomController {
    @Autowired
    private BannerService bannerService;

    @ApiOperation(value = "根据id查询排名前四的banner", notes = "讲查询出来的结果缓存到redis中")
    @GetMapping
    public ResponseBo getBannersTopDESC() {
        List<Banner> list = bannerService.getBannersTopDESC();
        return ResponseBo.ok().data("banners", list);
    }

    @GetMapping("/test")
    public Banner test() {
        //说明Json的配置生效
        return new Banner().setGmtCreate(LocalDateTime.now());
    }
}

