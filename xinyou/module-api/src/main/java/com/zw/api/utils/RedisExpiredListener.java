package com.zw.api.utils;


import com.zw.common.domain.Batch;
import com.zw.common.domain.Coupon;
import com.zw.common.domain.Order;
import com.zw.mapper.CouponMapper;
import com.zw.mapper.OrderMapper;
import com.zw.service.BatchService;
import com.zw.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class RedisExpiredListener extends KeyExpirationEventMessageListener {
    public static final String UNPAID_PREFIX = "unpaidOrder_";

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    BatchService batchService;
    @Autowired
    OrderService orderService;

    @Autowired
    CouponMapper couponMapper;

    public RedisExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern){
        String expiredKey = message.toString();
//        System.out.println("expiredKey:"+expiredKey);
        if(expiredKey.startsWith(UNPAID_PREFIX)){
            String sn = expiredKey.split("_")[1];
            //根据sn获取订单信息
            Order order= orderMapper.getOneBySn(sn);
            order.setStatus(3);
            // 设置取消订单的时间
            order.setCalcelTime(new java.sql.Timestamp(System.currentTimeMillis()));
            // 设置取消类别
            order.setCancelType(0);// 超时未支付

            //更新批次信息
            Batch batch = order.getCamperInfo().toJavaObject(Batch.class);
            // 获取订单数量
            int orderNum = order.getProductCount();
            if(batch.getRemains()==-1){
                batch.setRemains(0);
            }
            batch.setRemains(batch.getRemains()+orderNum);

            // 更新coupon信息,根据couponId获取coupon信息
            Coupon coupon = couponMapper.selectById(order.getCouponId());
            if(coupon!=null){
                coupon.setUseTime(null);
                coupon.setStatus(0);
                coupon.setUseOrderSn("0");
                couponMapper.updateById(coupon);
            }

            batchService.updateById(batch);
            orderService.updateById(order);
            System.out.println("订单已过期"+expiredKey+"已过期");
        }
    }
}
