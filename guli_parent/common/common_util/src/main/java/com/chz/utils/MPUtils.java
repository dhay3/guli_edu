package com.chz.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/28 19:55
 * @Description: TODO
 */
public class MPUtils {

    public static <T> Map<String, Object> getPageInfos(Page<T> page) {
        HashMap<String, Object> infos = new HashMap<>();
        infos.put("total", page.getTotal());
        infos.put("records", page.getRecords());
        infos.put("current", page.getCurrent());
        infos.put("size", page.getSize());
        infos.put("pages", page.getPages());
        infos.put("next", page.hasNext());
        infos.put("pre", page.hasPrevious());
        return infos;
    }
}
