package com.zw.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zw.common.domain.Order;

import java.util.List;

/**
* @author aking
* @description 针对表【mall_order】的数据库操作Service
* @createDate 2022-12-24 10:31:44
*/
public interface OrderService extends IService<Order> {
    //根据openid获取全部订单，并检查营期时间，如果endtime小于当前时间，则将订单状态改为已完成
    List<Order> checkOrderStatus(String openid);
}
