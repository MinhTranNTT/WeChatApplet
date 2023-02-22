package com.zw.backstage.controller;

import com.zw.common.domain.Order;
import com.zw.common.utils.JsonResult;
import com.zw.common.utils.annotation.Authorize;
import com.zw.mapper.OrderMapper;
import com.zw.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    // 获取所有订单
    @Authorize
    @GetMapping("/list")
    public JsonResult<List<Order>> getAllOrder(){
        return new JsonResult(200,orderMapper.getAllOrderByPayTimeDesc());
    }
}
