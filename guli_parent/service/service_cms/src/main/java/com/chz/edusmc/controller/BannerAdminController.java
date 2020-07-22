package com.chz.edusmc.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chz.edusmc.entity.domain.Banner;
import com.chz.edusmc.service.BannerService;
import com.chz.response.ResponseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author chz
 * @since 2020-07-20
 */
@Api("后台管理轮播图")
@Validated
@RestController
@CrossOrigin
@RequestMapping("/edusmc/banneradmin")
public class BannerAdminController {
    @Autowired
    private BannerService bannerService;

    /**
     * 分页插叙banner
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("/{currentPage}/{pageSize}")
    public ResponseBo pageBannerByCondition(@ApiParam(name = "currentPage", value = "当前的页数")
                                            @Min(1) @PathVariable("currentPage") Integer currentPage,
                                            @ApiParam(name = "pageSize", value = "页面显示条数")
                                            @PathVariable("pageSize") Integer pageSize) {
        Page<Banner> page = new Page<>();
        bannerService.page(page, null);
        List<Banner> banners = page.getRecords();
        long total = page.getTotal();
        return ResponseBo.ok().data("banners", banners).data("total", total);
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("{id}")
    public ResponseBo getBannerById(@PathVariable String id) {
        Banner banner = bannerService.getById(id);
        return ResponseBo.ok().data("banner", banner);
    }

    /**
     * 添加banner
     *
     * @param banner
     * @return
     */
    @ApiOperation(value = "新增Banner")
    @PostMapping
    public ResponseBo saveBanner(@RequestBody Banner banner) {
        return bannerService.save(banner) ? ResponseBo.ok() : ResponseBo.error();
    }

    /**
     * 修改banner
     *
     * @param banner
     * @return
     */
    @ApiOperation(value = "修改Banner")
    @PutMapping
    public ResponseBo updateBanner(@RequestBody Banner banner) {
        return bannerService.updateById(banner) ? ResponseBo.ok() : ResponseBo.error();
    }

    /**
     * 删除banner
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除Banner")
    @DeleteMapping("/{id}")
    public ResponseBo deleteBannerById(@PathVariable String id) {
        return bannerService.removeById(id) ? ResponseBo.ok() : ResponseBo.error();
    }
}

