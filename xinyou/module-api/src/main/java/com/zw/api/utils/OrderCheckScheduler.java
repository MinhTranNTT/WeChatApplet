package com.zw.api.utils;

import com.alibaba.fastjson.JSONObject;
import com.zw.common.domain.Batch;
import com.zw.common.domain.Order;
import com.zw.mapper.OrderMapper;
import com.zw.service.BatchService;
import com.zw.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderCheckScheduler {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderService orderService;
    @Autowired
    BatchService batchService;
//    @Scheduled(cron = "0/60 * * * * ?")
    public void checkOrder(){
        // 获取所有订单状态为未支付的订单 status=0；
        List<Order> unPaidOrder = orderMapper.getByStatus(0);
        for (Order order:unPaidOrder){
            // 获取订单创建时间
            long createTime = order.getCreateTime().getTime();
            // 获取当前时间
            long nowTime = System.currentTimeMillis();
            // 计算时间差
            long timeDiff = nowTime - createTime;
            // 如果时间差大于30分钟，将订单状态改为已取消 status=2
            if (timeDiff > 30*60*1000){
                order.setStatus(3);
                // 设置取消订单的时间
                order.setCalcelTime(new java.sql.Timestamp(System.currentTimeMillis()));
                // 设置取消类别
                order.setCancelType(0);// 超时未支付

                //更新批次信息
                Batch batch = order.getCamperInfo().toJavaObject(Batch.class);
                // 获取订单数量
                int orderNum = order.getProductCount();
                batch.setRemains(batch.getRemains()+orderNum);
                batchService.updateById(batch);
                orderService.updateById(order);
            }
        }
//        System.out.println("检查订单");
    }
}
