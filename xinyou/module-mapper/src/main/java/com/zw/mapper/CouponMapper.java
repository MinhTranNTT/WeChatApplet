package com.zw.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.common.domain.Coupon;
import org.apache.ibatis.annotations.Mapper;

/**
* @author aking
* @description 针对表【mall_coupon(优惠券)】的数据库操作Mapper
* @createDate 2022-12-28 11:26:53
* @Entity generator.domain.Coupon
*/
@Mapper
public interface CouponMapper extends BaseMapper<Coupon> {
    //根据userOpenid获取优惠券列表
    List<Coupon> getAllByUserOpenid(@Param("userOpenid") String userOpenid);
}




