package com.zw.api.controller;

import com.zw.api.utils.RedisExpiredListener;
import com.zw.common.domain.Batch;
import com.zw.common.domain.Order;
import com.zw.common.utils.JsonResult;
import com.zw.common.utils.annotation.Authorize;
import com.zw.mapper.OrderMapper;
import com.zw.service.BatchService;
import com.zw.service.OrderService;
import com.zw.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    BatchService batchService;

    @Autowired
    RedisService redisService;
    @Autowired
    RedisExpiredListener redisExpiredListener;
    // 获取订单列表
    @GetMapping("/list")
    public JsonResult<List<Order>> getOrderList(){
        return new JsonResult<>(200,orderService.list());
    }

    // 根据sn更新订单信息
    @PutMapping("/update")
    public JsonResult<Boolean> updateOrder(@RequestBody Order order){
        return new JsonResult<>(200,orderMapper.updateBySn(order));
    }

    // 根据订单状态和用户openid获取订单列表
    @Authorize
    @GetMapping("/list/{status}")
    public JsonResult<List<Order>> getOrderListByStatusAndOpenid(@PathVariable("status") Integer status, HttpServletRequest request){
        String openid = request.getAttribute("openid").toString();
        return new JsonResult<>(200,orderMapper.getAllByStatusAndOpenid(status,openid));
    }
    // 根据用户openid获取订单列表
    @Authorize
    @GetMapping("/getList")
    public JsonResult<List<Order>> getOrderListByOpenid(HttpServletRequest request){
        String openid = request.getAttribute("openid").toString();
        return new JsonResult<>(200,orderService.checkOrderStatus(openid));
    }

    // 根据订单id获取订单信息
    @Authorize
    @GetMapping("/get/{id}")
    public JsonResult<Order> getOrderById(@PathVariable("id") Integer id){
        return new JsonResult<>(200,orderService.getById(id));
    }

    // 取消订单
    @Authorize
    @PutMapping("/cancel")
    public JsonResult<Boolean> cancelOrder(@RequestBody Order order){
        order.setStatus(3); //将订单状态设置为已取消
        order.setCalcelTime(new Date()); //设置取消时间
        order.setCancelType(2); // 设置取消类型为用户取消
        Batch batch = order.getCamperInfo().toJavaObject(Batch.class);
        batch.setRemains(batch.getRemains()+order.getProductCount()); // 将剩余名额加回去
        batchService.updateById(batch);
        redisService.del(redisExpiredListener.UNPAID_PREFIX+order.getSn());
        return new JsonResult<>(200,orderService.updateById(order));
    }

    // 删除订单
    @Authorize
    @PutMapping("/delete")
    public JsonResult<Boolean> deleteOrder(@RequestBody Order order){
        // 将订单的isDelete设置为1
        order.setIsDelete(1);
        return new JsonResult<>(200,orderService.updateById(order));
    }


}
