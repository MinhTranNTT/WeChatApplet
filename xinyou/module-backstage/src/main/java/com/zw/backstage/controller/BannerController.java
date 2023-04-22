package com.zw.backstage.controller;

import com.zw.common.domain.Banner;
import com.zw.common.domain.Batch;
import com.zw.common.utils.JsonResult;
import com.zw.common.utils.annotation.Authorize;
import com.zw.mapper.BannerMapper;
import com.zw.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.cache.annotation.CacheRemoveAll;
import java.util.List;

@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    @Autowired
    BannerMapper bannerMapper;

    //获取 Banner 列表
    @Authorize
    @GetMapping("/list")
    public JsonResult<List<Batch>> getAllBatch(){
        return new JsonResult(200,bannerMapper.getBannerListBySort());
    }

    // 根据id删除banner
    @Authorize
    @DeleteMapping("/delete/{id}")
    @CacheRemoveAll(cacheName = "products")
    public JsonResult<Void> delete(@PathVariable("id") Integer id){
        bannerService.removeById(id);
        return new JsonResult<>(200);
    }

    // 根据id修改banner
    @Authorize
    @PutMapping("/update")
    @CacheRemoveAll(cacheName = "products")
    public JsonResult<Void> update(@RequestBody Banner banner){
        bannerService.updateById(banner);
        return new JsonResult<>(200);
    }

    // 添加banner
    @Authorize
    @PostMapping("/add")
    @CacheRemoveAll(cacheName = "products")
    public JsonResult<Void> add(@RequestBody Banner banner){
        bannerService.save(banner);
        return new JsonResult<>(200);
    }
}
