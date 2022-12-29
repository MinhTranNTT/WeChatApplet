package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Coupon;
import generator.service.CouponService;
import generator.mapper.CouponMapper;
import org.springframework.stereotype.Service;

/**
* @author aking
* @description 针对表【mall_coupon(优惠券)】的数据库操作Service实现
* @createDate 2022-12-28 11:57:34
*/
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon>
    implements CouponService{

}




