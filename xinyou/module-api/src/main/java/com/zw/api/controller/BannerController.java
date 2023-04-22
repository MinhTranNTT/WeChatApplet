package com.zw.api.controller;

import com.zw.common.domain.Banner;
import com.zw.common.utils.JsonResult;
import com.zw.mapper.BannerMapper;
import com.zw.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    @Autowired
    BannerMapper bannerMapper;

    //获取 Banner 列表
    @Cacheable(value = "products", key = "'banner'")
    @GetMapping("/list")
    public JsonResult<List<Banner>> getBannerList() {
        return new JsonResult<>(200,bannerMapper.getBannerListBySort());
    }

//    添加banner
    @PostMapping("/set")
    JsonResult<Boolean> setBanner(@RequestBody Banner banner){
        boolean flag = bannerService.save(banner);
        return new JsonResult<>(flag?200:500);
    }

//    修改banner
    @PutMapping("/update")
    JsonResult<Boolean> updateBanner(@RequestBody Banner banner){
        boolean flag = bannerService.updateById(banner);
        return new JsonResult<>(flag ? 200 : 500);
    }

//    删除banner
    @DeleteMapping("/delete/{id}")
    JsonResult<Boolean> deleteBannerId(@PathVariable Integer id){
        boolean flag = bannerService.removeById(id);
        return new JsonResult<>(flag ? 200 : 500);
    }
    //根据id获取单个banner
    @GetMapping("/getOne/{id}")
    JsonResult<Banner> getOneBanner(@PathVariable Integer id) {
        return new JsonResult<>(200, bannerService.getById(id));
    }
}
