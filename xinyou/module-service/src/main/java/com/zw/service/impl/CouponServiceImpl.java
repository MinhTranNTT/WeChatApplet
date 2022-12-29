package com.zw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zw.common.domain.Coupon;
import com.zw.mapper.CouponMapper;
import com.zw.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* @author aking
* @description 针对表【mall_coupon(优惠券)】的数据库操作Service实现
* @createDate 2022-12-28 11:26:53
*/
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon>
    implements CouponService {

    @Autowired
    private CouponMapper couponMapper;
    @Override
    public int createNewerCoupon(String openid) {
        //将优惠券类型设置为折扣券，折扣率为 8 折，有效期为 30 天，状态为未使用
        Coupon coupon = new Coupon();
        coupon.setCouponType(1);//新人优惠券类型为折扣券
        coupon.setUserOpenid(openid);
        coupon.setCouponName("新人优惠券");
        coupon.setCouponCode("NEWER_COUPON");
        coupon.setCouponDesc("新人专享");
        coupon.setGoodsType(0); //全品类
        coupon.setDiscount(BigDecimal.valueOf(0.8)); //折扣率为 8 折
        coupon.setWhetherForbidPreference(0); //不可与其他优惠叠加
        coupon.setStatus(0); //未使用
        coupon.setGetType(0); //新人券
        coupon.setFetchTime(new Date());
        coupon.setStartTime(new Date());
        coupon.setEndTime(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000));
        couponMapper.insert(coupon);
        return 0;
    }

    @Override
    public List<Coupon> checkCouponStatus(String openid) {
        List<Coupon> couponList = couponMapper.getAllByUserOpenid(openid);
        for (Coupon coupon : couponList) {
            Date endTime = coupon.getEndTime();
            if (coupon.getStatus()==0 && endTime.before(new Date())) {
                coupon.setStatus(2);
                couponMapper.updateById(coupon);
            }
        }
        return couponList;
    }

    //
}




