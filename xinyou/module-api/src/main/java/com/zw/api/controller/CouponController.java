package com.zw.api.controller;

import com.zw.common.domain.Coupon;
import com.zw.common.utils.JsonResult;
import com.zw.common.utils.annotation.Authorize;
import com.zw.mapper.CouponMapper;
import com.zw.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;
    @Autowired
    CouponMapper couponMapper;

    // 根据openid获取优惠券列表
    @Authorize
    @GetMapping("/list")
    public JsonResult<List<Coupon>> getCouponList(HttpServletRequest request){
        String openid = request.getAttribute("openid").toString();
        return new JsonResult<>(200,couponService.checkCouponStatus(openid));
    }
}
