package com.zw.backstage.controller;

import com.zw.common.domain.Ads;
import com.zw.common.domain.Banner;
import com.zw.common.utils.JsonResult;
import com.zw.common.utils.annotation.Authorize;
import com.zw.service.AdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adv")
public class AdsController {
    @Autowired
    AdsService advService;

    // 根据id修改广告
    @Authorize
    @PutMapping("/update")
    public JsonResult<Void> update(@RequestBody Ads ads){
        advService.updateById(ads);
        return new JsonResult<>(200);
    }

    // 获取列表
    @Authorize
    @GetMapping("/list")
    public JsonResult<List<Ads>> getAllAdv(){
        return new JsonResult<List<Ads>>(200,advService.list());
    }

}
