package com.zw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zw.common.domain.Batch;
import com.zw.common.domain.Order;
import com.zw.mapper.OrderMapper;
import com.zw.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author aking
* @description 针对表【mall_order】的数据库操作Service实现
* @createDate 2022-12-24 10:31:44
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService {


    @Autowired
    private OrderMapper orderMapper;
    @Override
    public List<Order> checkOrderStatus(String openid) {
        List<Order> orderList = orderMapper.getAllByOpenidOrderByCreateTimeDesc(openid);
        for (Order order : orderList) {
            Batch batch = order.getCamperInfo().toJavaObject(Batch.class);
            Date endTime = batch.getEndTime();
            if (order.getStatus()==1 && endTime.before(new Date())) {
                order.setStatus(2);
                order.setFinishTime(endTime);
                orderMapper.updateById(order);
            }
        }
        return orderList;
    }
}




