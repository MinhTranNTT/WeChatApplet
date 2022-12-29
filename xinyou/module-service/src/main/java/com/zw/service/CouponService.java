package com.zw.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zw.common.domain.Coupon;

import java.util.List;

/**
* @author aking
* @description 针对表【mall_coupon(优惠券)】的数据库操作Service
* @createDate 2022-12-28 11:26:53
*/
public interface CouponService extends IService<Coupon> {

    // 刚登录时生成新人优惠券
    int createNewerCoupon(String openid);

    // 获取用户的所有优惠券时，检查优惠券的状态是否过期
    List<Coupon>  checkCouponStatus(String openid);

}
