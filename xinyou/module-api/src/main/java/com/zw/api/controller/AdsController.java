package com.zw.api.controller;

import com.zw.common.domain.Ads;
import com.zw.common.utils.JsonResult;
import com.zw.mapper.AdsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ads")
public class AdsController {

    @Autowired
    AdsMapper adsMapper;

    // 获取ads
    @GetMapping("/getAds")
    public JsonResult<Ads> getAds(){
        return new JsonResult<>(200,adsMapper.selectOneByIsShowLimit1());
    }
}
